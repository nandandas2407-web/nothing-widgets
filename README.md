# Nothing Widgets (Android)

Native Android home-screen widgets in the Nothing style (dot-matrix NDot font, red accent),
ported from the KDE Plasma "nothing-kde-widgets" designs.

Widgets: Digital Clock (pill), Digital Clock (large), Analog Clock, Date, Battery.

## Get the APK - see PUSH_TO_GITHUB.md (commands) or:
1. Create a new GitHub repo and push this folder to it (branch `main`).
2. Open the repo's **Actions** tab - "Build APK" runs automatically (about 3-5 min).
3. Download `NothingWidgets.apk` from the repo's **Releases** page ("Nothing Widgets - latest build").
4. Install it, open the app, tap a widget to pin it. Or long-press home > Widgets > Nothing Widgets.

## Or build locally
Android Studio: open this folder and run Build > Build APK(s).
Command line (JDK 17 + Android SDK 34 + Gradle 8.9): `gradle assembleDebug`
Output: app/build/outputs/apk/debug/app-debug.apk

Licence: GPL-3.0 (from the original project). Nothing fonts are Nothing Technology Limited's - personal, non-commercial use.
