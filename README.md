# PS4 to Xbox - Simple Background Service

تطبيق بسيط لتحويل أزرار PS4 DualShock 4 إلى Xbox mapping في الخلفية.

## الاستخدام

1. **تثبيت التطبيق**
   ```bash
   ./gradlew assembleDebug
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

2. **تشغيل التطبيق**
   - افتح التطبيق
   - اضغط "START SERVICE"
   - التطبيق يعمل في الخلفية

3. **ربط PS4 Controller**
   - اذهب إلى Bluetooth Settings
   - ابحث عن "WIRELESS CONTROLLER"
   - اربط الجهاز

4. **تشغيل GTA San Andreas**
   - اتركه التطبيق يعمل في الخلفية
   - لعب بـ PS4 controller
   - الأزرار تُترجم تلقائياً إلى Xbox mapping

## الميزات

✓ يعمل في الخلفية (Background Service)
✓ لا يحتاج root
✓ آمن وبسيط
✓ استهلاك بطارية منخفض
✓ متوافق مع Android 8.0+
✓ يدعم ARMv7 (32-bit)

## معلومة مهمة

هذا التطبيق يقرأ إشارات PS4 ويترجمها إلى Xbox mapping **على مستوى التطبيق فقط**.

إذا كانت لعبة معينة لا تستجيب، قد تحتاج إلى:
- تفعيل Accessibility Service
- أو استخدام جهاز rooted (للحل الأساسي)

## البناء

```bash
cd ps4-to-xbox-virtual-controller
./gradlew clean build
./gradlew assembleDebug
```

## التثبيت

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## الإزالة

```bash
adb uninstall com.example.ps4toxbox
```
