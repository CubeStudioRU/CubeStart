<h1 align="center">
  <img src="images/banner.svg" alt="Cube Api"/>
  CubeStart
  <br/>
</h1>

<p align="center">
    CLI приложение, которое поможет вам играть в актуальные версии сборок  CubeStudio. <i>Использует Cube-API</i>
</p>

## Последний релиз: ...
* ...

## Дорожная карта CubeStart
* [ ] Первый запуск приложения
* [ ] Возможность обновления, удаления, загрузки модов
* [ ] Возможность добавления своих, кастомных модов в сборку путем отдельной папки ```custom_mods``` в директории сборки
* [ ] Добавить проверку сборки на целостность
* [ ] Создание .exe файла приложения
* [ ] Добавить возможность авто-загрузки готовых профилей в разные лаунчеры (MultiMC, PrismLauncher, Modrinth App, CurseForge Launcher, )
* [ ] Добавить возможность обладания несколькими обновляемыми сборками одновременно (зависит от Cube-API)

## Зависимости
* ```...```, version >= ...

## Файл конфигурации config.json
```
{
  "instance_id":0, // ID текущей установленной сборки (Не трогать!)
  "instance_version":"0.0.9", // Текущая версия установленной сборки (Не трогать!)
  "instance_directory":"/home/fadegor05/.local/share/PrismLauncher/instances/1.20.1/.minecraft", // Путь до директории вашей сборки
  "api_url":"http://127.0.0.1:8000/api/v1" // Адрес Cube-API (Не изменяйте параметр, если не знаете, что вы делаете!)
}
```

## Полезные ссылки
* Телеграм-канал CubeStudio: https://t.me/+Gphg_BIJEdMwMmFi

###### Not an official Minecraft product. We are in no way affiliated with or endorsed by Mojang Synergies AB, Microsoft Corporation or other rightsholders.