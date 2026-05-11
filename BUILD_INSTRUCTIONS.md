# Инструкции по сборке Astral Sorcery для 1.21.1 NeoForge

## Требования

- Java 17 или выше (Java 21 рекомендуется)
- Gradle 8.8 или выше
- Git
- SDKMAN (опционально, для управления версиями Java и Gradle)

## Шаги для сборки

### 1. Подготовка окружения

Если используется SDKMAN:

```bash
# Установить Java 17 (для запуска Gradle)
source /usr/local/sdkman/bin/sdkman-init.sh
sdk install java 17.0.19-ms
sdk install gradle 8.8

# Установить Java 21 (для компиляции)
sdk install java 21.0.10-ms
```

### 2. Очистить кэш Gradle (при необходимости)

```bash
cd /workspaces/AstralSorcery
rm -rf .gradle
```

### 3. Собрать проект

С использованием SDKMAN:

```bash
export JAVA_HOME=/usr/local/sdkman/candidates/java/17.0.19-ms
/usr/local/sdkman/candidates/gradle/8.8/bin/gradle clean build
```

Или со встроенным gradlew (требует исправления):

```bash
# Убедитесь, что gradlew исправлен (смотрите раздел "Найденные проблемы")
./gradlew clean build
```

### 4. Результаты сборки

Скомпилированный JAR файл будет находиться в:
```
build/libs/astralsorcery-1.21.1-1.20.0.jar
```

## Найденные проблемы и исправления

### 1. ✅ ИСПРАВЛЕНО: gradlew скрипт (JVM опции с лишними кавычками)
- **Файл**: `gradlew`  
- **Проблема**: `DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'` (неправильный синтаксис)
- **Исправление**: `DEFAULT_JVM_OPTS='-Xmx64m -Xms64m'`
- **Статус**: ✅ ИСПРАВЛЕНО

### 2. ✅ ИСПРАВЛЕНО: Неправильная версия Curios
- **Файл**: `build.gradle`  
- **Проблема**: `implementation 'top.theillusivec4.curios:curios-neoforge:9.5.1+1.21.1-api'` (суффикс -api некорректен)
- **Исправление**: `implementation 'top.theillusivec4.curios:curios-neoforge:9.5.1+1.21.1'`
- **Статус**: ✅ ИСПРАВЛЕНО

### 3. ✅ ИСПРАВЛЕНО: Структура классов-реестров (нарушена структура файлов)
- **Файлы**: 
  - `src/main/java/hellfirepvp/astralsorcery/common/registry/RegistryDataSerializers.java`
  - `src/main/java/hellfirepvp/astralsorcery/common/registry/RegistryConstellations.java`
  - `src/main/java/hellfirepvp/astralsorcery/common/registry/RegistryStructureTypes.java`
- **Проблема**: Методы находились вне классов (нарушена структура)
- **Исправление**: Восстановлена правильная структура классов с закрывающими скобками на правильных местах
- **Статус**: ✅ ИСПРАВЛЕНО

### 4. ⚠️ ТРЕБУЕТСЯ ИСПРАВЛЕНИЯ: Импорты и API классов

Обнаружены следующие проблемы с миграцией API на 1.21 NeoForge:

#### a) MixinEntity.java  
- Отсутствуют импорты классов (Level, Vector3d, AxisAlignedBB, ISelectionContext, ReuseableStream, VoxelShape)
- **Требуется**: Проверить NeoForge 1.21 API и обновить импорты

#### b) MixinForgeHooks.java  
- Пакеты изменены в Minecraft 1.21 / NeoForge
- Требуемые обновления:
  - `net.minecraft.world.entity` → проверить в NeoForge 1.21
  - `net.minecraft.item` → проверить в NeoForge 1.21
  - Другие пакеты Minecraft
- **Требуется**: Полная миграция импортов и API вызовов

#### c) Другие файлы Mixin
- Похожие проблемы с импортами в других mixin файлах
- **Требуется**: Систематическое обновление всех обращений к Minecraft/NeoForge API

## Рекомендации для продолжения портирования

1. **Обновить импорты API**: Все классы Minecraft и NeoForge должны быть проверены на совместимость с версией 1.21.1
2. **Проверить Mixin инъекции**: Некоторые классы и методы Minecraft могут быть переименованы
3. **Обновить EventBus события**: NeoForge может изменить систему событий
4. **Проверить конфигурацию**: Убедиться, что все параметры конфигурации ещё актуальны
5. **Тестирование**: После сборки необходимо тестирование функционала в игре

## Полезные команды

```bash
# Полная чистая сборка
gradle clean build

# Компилировать исходный код
gradle compileJava

# Запустить data generator
gradle runData

# Просмотреть информацию о сборке
gradle tasks

# Сборка с подробным выводом ошибок
gradle build --stacktrace
```

## Ссылки

- [NeoForge Documentation](https://docs.neoforged.net/)
- [Minecraft 1.21 Wiki](https://minecraft.wiki)
- [Gradle User Guide](https://docs.gradle.org/)
