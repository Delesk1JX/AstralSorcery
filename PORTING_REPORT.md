# Отчет о Портировании Astral Sorcery на NeoForge 1.21.1

## ✅ ИСПРАВЛЕННЫЕ ПРОБЛЕМЫ

### 1. ✓ ForgeHooks CoreMods удалены
- **Файл**: [src/main/resources/META-INF/coremods.json](src/main/resources/META-INF/coremods.json)
- **Проблема**: Использовали устаревший API ForgeHooks (`net.minecraftforge.coremod.api.ASMAPI`)
- **Решение**: Отключены, так как NeoForge использует Mixins вместо коремодов
- **Статус**: ✅ ЗАКРЫТО

### 2. ✓ ObserverLib включена как зависимость
- **Файл**: [build.gradle](build.gradle)
- **Проблема**: Зависимость была закомментирована, но активно используется в коде (15+ файлов)
- **Решение**: Раскомментирована и включена как `compileOnly` + `runtimeOnly`
- **Статус**: ✅ ЗАКРЫТО

### 3. ✓ MixinConnector создан
- **Файл**: [src/main/java/hellfirepvp/astralsorcery/MixinConnector.java](src/main/java/hellfirepvp/astralsorcery/MixinConnector.java)
- **Проблема**: Класс не существовал, требуется для инициализации Mixins
- **Решение**: Создан и включен в manifest jar
- **Статус**: ✅ ЗАКРЫТО

### 4. ✓ Build.gradle переформатирован для NeoForge
- **Файл**: [build.gradle](build.gradle)
- **Проблема**: Синтаксис был смешанный (Forge + NeoForge)
- **Решение**: Переписан на правильной NeoForge синтаксис (`neoForge { }` блок)
- **Статус**: ✅ ЗАКРЫТО

---

## ⚠️ ВОЗМОЖНЫЕ ПРОБЛЕМЫ, ТРЕБУЮЩИЕ ВНИМАНИЯ

### 1. CommonProxy и ClientProxy архитектура
- **Файлы**: [src/main/java/hellfirepvp/astralsorcery/common/CommonProxy.java](src/main/java/hellfirepvp/astralsorcery/common/CommonProxy.java), [src/main/java/hellfirepvp/astralsorcery/client/ClientProxy.java](src/main/java/hellfirepvp/astralsorcery/client/ClientProxy.java)
- **Проблема**: Используется паттерн Proxy с `SidedThreadGroups` - может быть несовместим в NeoForge 1.21.1
- **Рекомендация**: Проверить, что классы используют правильные API NeoForge для side-specific кода
- **Действие**: Нужно вручную проверить

### 2. Версии зависимостей
- **Зависимости**: ObserverLib, Curios, JEI, Patchouli
- **Проблема**: Версии могут быть устаревшие или недоступны в мавене  
- **Рекомендация**: Проверить актуальность:
  - `hellfirepvp.observerlib:observerlib:1.21.1-2.0.0` - может не существовать
  - `top.theillusivec4.curios:curios-neoforge:9.5.1+1.21.1` - проверить в maven
  - `mezz.jei:jei-1.21.1-*:19.27.0.340` - проверить версию

### 3. Gradle и NeoGradle совместимость
- **Текущая конфигурация**: Gradle 8.3 + NeoGradle 7.0.153
- **Проблема**: При сборке возникают ошибки несовместимости
- **Рекомендация**: Использовать проверенные версии:
  - Для NeoForge 1.21.1: используйте Gradle 8.1-8.3 и NeoGradle 7.0.x
  - Или обновиться на более свежие версии если доступны

### 4. Mixins конфигурация
- **Файл**: [src/main/resources/assets/astralsorcery/astralsorcery.mixins.json](src/main/resources/assets/astralsorcery/astralsorcery.mixins.json)
- **Проблема**: Требуется проверить, что все перечисленные миксины существуют и работают
- **Действие**: Нужно запустить сборку и посмотреть на ошибки компиляции мixin-ов

---

## 📋ЧЕК-ЛИСТ СЛЕДУЮЩИХ ДЕЙСТВИЙ

- [ ] Проверить наличие всех зависимостей в мавене (ObserverLib, Curios, JEI)
- [ ] Запустить сборку и исправить ошибки компиляции
- [ ] Проверить CommonProxy/ClientProxy использование SidedThreadGroups
- [ ] Проверить все Mixin файлы на совместимость
- [ ] Удалить или модифицировать JS коремоды ([reach_set_client_renderer.js](src/main/resources/coremods/reach_set_client_renderer.js), [reach_set_server_entity_interact.js](src/main/resources/coremods/reach_set_server_entity_interact.js))
- [ ] Протестировать сборку `gradle build`
- [ ] Протестировать запуск клиента `gradle runClient`
- [ ] Протестировать запуск сервера `gradle runServer`

---

## 🔧БЫСТРЫЕ РЕШЕНИЯ ПРОБЛЕМ

### Если сборка не работает из-за зависимостей обновить dependencies:
```gradle
dependencies {
    // Проверьте актуальные версии на официальных мавен репозиториях:
    // https://maven.hellfiredev.net/ - для ObserverLib
    // https://maven.theillusivec4.top/ - для Curios
    // https://dvs1.progwml6.com/files/maven - для JEI
}
```

### Если есть ошибки с Gradle:
```bash
# Очистите кеш Gradle
rm -rf .gradle

# Пересоберите
gradle clean build
```

### Если мод не загружается:
- Проверьте [src/main/resources/META-INF/mods.toml](src/main/resources/META-INF/mods.toml)
- Убедитесь, что все зависимостьиdose помечены правильно

---

**Последнее обновление**: 11 май 2026
**Статус портирования**: ~70% - основные проблемы исправлены, требуется тестирование
