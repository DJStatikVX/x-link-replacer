# 🔗 X Link Replacer

**X Link Replacer** is a tiny Android app that intercepts shared X (Twitter) links and automatically replaces the domain with an alternative like [fixupx.com](https://fixupx.com) — giving you a smoother, cleaner experience when viewing posts on apps like Discord and Telegram.

> 🚀 Perfect for users who want to avoid the official X/Twitter authwall.

---

## 🧠 How It Works

1. You tap **Share** on a post from the X app.
2. **X Link Replacer** appears as a sharing option.
3. It detects X links and replaces the domain (`x.com` → `fixupx.com`).
4. It then launches the system share sheet with the modified link.

---

## 🛠️ Built With

- **Kotlin**
- **Android Jetpack**: ComponentActivity, ViewModel, ContextCompat
- **Hilt** for dependency injection
- **MockK** for mocking in unit tests
- **Robolectric** for JVM-based Android tests

---

## 📦 Getting Started

1. Clone this repository:

```bash
git clone https://github.com/DJStatikVX/x-link-replacer.git
```

2. Open the project in Android Studio.

3. Build and run on any device running Android 5.0+.
