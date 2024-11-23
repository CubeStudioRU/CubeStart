#!/bin/bash

# Запрашиваем название инстанса
read -p "Введите название Сборки: " instance_name

# Задаем переменные для путей
instance_dir="$HOME/Library/Application Support/PrismLauncher/instances/$instance_name"
base_dir="$instance_dir/.minecraft"
config_file="$instance_dir/instance.cfg"

# Проверяем, существует ли директория инстанса
if [[ ! -d "$instance_dir" ]]; then
    echo "Сборки '$instance_name' не существует."
    exit 1
fi

# Скачиваем последнюю версию CubeStart
echo "Получаем последнюю версию CubeStart..."
latest_release_url=$(curl -s https://api.github.com/repos/CubeStudioRU/CubeStart/releases/latest | grep "browser_download_url" | grep "CubeStart.*\.jar" | cut -d '"' -f 4)

if [[ -z "$latest_release_url" ]]; then
    echo "Не удалось найти ссылку на последнюю версию CubeStart."
    exit 1
fi

file_name=$(basename "$latest_release_url")

echo "Скачиваем $file_name..."
curl -L -o "$file_name" "$latest_release_url"

# Проверяем, скачался ли файл
if [[ ! -f "$file_name" ]]; then
    echo "Ошибка при скачивании файла."
    exit 1
fi

# Создаем директорию .minecraft, если ее нет
echo "Создаем директорию $base_dir, если ее нет..."
mkdir -p "$base_dir"

# Перемещаем файл в папку
echo "Перемещаем $file_name в $base_dir..."
mv "$file_name" "$base_dir/"

# Обновляем instance.cfg
echo "Обновляем файл instance.cfg..."
if [[ -f "$config_file" ]]; then
    # Удаляем старую строку PreLaunchCommand, если она есть
    sed -i '' '/^PreLaunchCommand/d' "$config_file"
    sed -i '' '/^OverrideCommands/d' "$config_file"
else
    # Если файл не существует, создаем его
    touch "$config_file"
fi

# Проверяем наличие секции [General] и добавляем ее, если нужно
if ! grep -q "^\[General\]" "$config_file"; then
    echo "[General]" >> "$config_file"
fi

# Добавляем новую строку PreLaunchCommand в секцию [General]
sed -i '' "/^\[General\]/a\\
PreLaunchCommand=java -jar $file_name --skip --dir=\"\$INST_MC_DIR\"\\
OverrideCommands=true
" "$config_file"

echo "Готово! CubeStart успешно установлен и настроен для Instance '$instance_name'."
