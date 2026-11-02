# Create repo + push + get APK (Termux)

pkg install git gh -y
gh auth login            # choose GitHub.com > HTTPS > login with browser/code
git config --global user.name "Nandan Das"
git config --global user.email "you@example.com"

cd NothingWidgets
git init
git add .
git commit -m "Nothing Widgets"
git branch -M main
gh repo create nothing-widgets --public --source=. --remote=origin --push

# wait 3-5 min, then:
gh run watch
gh release download latest -p NothingWidgets.apk
# APK is now in the folder. Install it:  termux-open NothingWidgets.apk
