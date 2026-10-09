# PS4 to Xbox - Simple Button Mapper

## الاستخدام البسيط

```bash
# البناء
./gradlew assembleDebug

# التثبيت
adb install app/build/outputs/apk/debug/app-debug.apk
```

## خطوات التشغيل

1. **اربط PS4 Controller عبر Bluetooth**
   - اذهب إلى Settings → Bluetooth
   - ابحث عن "WIRELESS CONTROLLER"
   - اختر وارتبط

2. **افتح البرنامج**
   - اضغط "Scan for PS4" - للتأكد من الاتصال
   - اضغط "Start Service" - لتشغيل الخدمة

3. **افتح GTA San Andreas**
   - الخدمة تعمل في الخلفية
   - استخدم PS4 بشكل طبيعي
   - الأزرار تُترجم تلقائياً إلى Xbox

## خريطة الأزرار

| PS4 | Xbox |
|-----|------|
| Cross | A |
| Circle | B |
| Square | X |
| Triangle | Y |
| L1 | LB |
| R1 | RB |
| Share | Back |
| Options | Start |
| PS Button | Guide |

## الميزات

✓ لا يحتاج Root
✓ يعمل في الخلفية
✓ استهلاك بطاري منخفض
✓ متوافق مع Android 8.0+
✓ يدعم ARMv7 (32-bit)

## الإزالة

```bash
adb uninstall com.example.ps4toxbox
```
