# Запрашиваем название инстанса
$instance_name = Read-Host "Введите название Сборки"

# Задаем переменные для путей
$modrinth_dir = "$env:APPDATA\ModrinthApp"
$instance_dir = "$modrinth_dir\profiles\$instance_name"

$base_dir = "$instance_dir"
$db_file = "$modrinth_dir\app.db"

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

### ЗДЕСЬ
# Путь к SQLite DLL
$sqlitePath = Join-Path $PSScriptRoot "System.Data.SQLite.dll"
if (-not (Test-Path $sqlitePath)) {
    Write-Host "Файл System.Data.SQLite.dll не найден. Убедитесь, что он находится в папке со скриптом."
    exit 1
}

# Загрузка библиотеки SQLite
Add-Type -Path $sqlitePath

# Подключаемся к базе данных SQLite и обновляем значение
Write-Host "Обновляем базу данных..."

# Создаем подключение к базе данных
$db_connection = New-Object System.Data.SQLite.SQLiteConnection
$db_connection.ConnectionString = "Data Source=$db_file;Version=3;"
$db_connection.Open()

# Создаем SQL-запрос для обновления таблицы profiles
$sql_query = @"
UPDATE profiles
SET override_hook_pre_launch = 'java -jar $base_dir\$file_name --skip --dir $base_dir'
WHERE path = '$instance_name';
"@

# Выполняем запрос
$command = $db_connection.CreateCommand()
$command.CommandText = $sql_query
$result = $command.ExecuteNonQuery()

if ($result -eq 0) {
    Write-Host "Не удалось обновить запись в базе данных. Проверьте, существует ли путь $instance_name в таблице profiles."
} else {
    Write-Host "База данных успешно обновлена. Изменено записей: $result."
}

# Закрываем соединение
$db_connection.Close()



Write-Host "Готово! CubeStart успешно установлен и настроен для Instance '$instance_name'."
