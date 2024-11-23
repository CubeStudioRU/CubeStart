# Запрашиваем название инстанса
$instance_name = Read-Host "Введите название Сборки"

# Задаем переменные для путей
$instance_dir = "$env:APPDATA\PrismLauncher\instances\$instance_name"
$base_dir = "$instance_dir\.minecraft"
$config_file = "$instance_dir\instance.cfg"

# Проверяем, существует ли директория инстанса
if (-not (Test-Path $instance_dir)) {
    Write-Host "Сборки '$instance_name' не существует."
    exit 1
}

# Скачиваем последнюю версию CubeStart
Write-Host "Получаем последнюю версию CubeStart..."
$latest_release_url = Invoke-RestMethod -Uri "https://api.github.com/repos/CubeStudioRU/CubeStart/releases/latest" |
    Where-Object { $_.assets.browser_download_url -match "CubeStart.*\.jar" } |
    Select-Object -ExpandProperty assets | Select-Object -ExpandProperty browser_download_url

if (-not $latest_release_url) {
    Write-Host "Не удалось найти ссылку на последнюю версию CubeStart."
    exit 1
}

$file_name = [System.IO.Path]::GetFileName($latest_release_url)

Write-Host "Скачиваем $file_name..."
Invoke-WebRequest -Uri $latest_release_url -OutFile $file_name

# Проверяем, скачался ли файл
if (-not (Test-Path $file_name)) {
    Write-Host "Ошибка при скачивании файла."
    exit 1
}

# Создаем директорию .minecraft, если ее нет
Write-Host "Создаем директорию $base_dir, если ее нет..."
if (-not (Test-Path $base_dir)) {
    New-Item -ItemType Directory -Path $base_dir
}

# Перемещаем файл в папку
Write-Host "Перемещаем $file_name в $base_dir..."
Move-Item -Path $file_name -Destination $base_dir -Force

# Обновляем instance.cfg
Write-Host "Обновляем файл instance.cfg..."
if (Test-Path $config_file) {
    (Get-Content $config_file) | Where-Object { $_ -notmatch "^PreLaunchCommand" } | Set-Content $config_file
    (Get-Content $config_file) | Where-Object { $_ -notmatch "^OverrideCommands" } | Set-Content $config_file
    # Читаем все строки файла
    $config_lines = Get-Content $config_file

    # Ищем индекс секции [General]
    $general_section_index = $config_lines.IndexOf('[General]')

    if ($general_section_index -ne -1) {
        # Если секция [General] существует, добавляем команду после нее
        $config_lines = $config_lines[0..$general_section_index] + @(
            'PreLaunchCommand=java -jar ' + $file_name + ' --skip --dir="$INST_MC_DIR"',
            "`nOverrideCommands=true"
        ) + $config_lines[($general_section_index + 1)..($config_lines.Length - 1)]
    } else {
        # Если секция [General] не найдена, добавляем ее с командами
        $config_lines = $config_lines + @(
            "[General]",
            'PreLaunchCommand=java -jar ' + $file_name + ' --skip --dir="$INST_MC_DIR"',
            "`nOverrideCommands=true"
        )
    }

    # Перезаписываем файл конфигурации
    $config_lines | Set-Content $config_file
} else {
    # Если файл не существует, создаем его
    New-Item -ItemType File -Path $config_file
    Add-Content -Path $config_file -Value "[General]"
    Add-Content -Path $config_file -Value "PreLaunchCommand=java -jar $file_name --skip --dir=""$INST_MC_DIR"""
    Add-Content -Path $config_file -Value "OverrideCommands=true"
}


Write-Host "Готово! CubeStart успешно установлен и настроен для Instance '$instance_name'."
