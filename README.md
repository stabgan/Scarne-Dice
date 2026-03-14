# 🎲 Scarne's Dice

A turn-based dice game for Android where you compete against a simple AI opponent.

## How It Works

Scarne's Dice follows classic pig-dice rules:

- **Roll** the die to accumulate turn points (values 2-6 are added to your turn score)
- **Rolling a 1** forfeits your turn score, but your banked total stays safe
- **Hold** to bank your turn score and pass the turn to the computer
- After both players take a turn, the higher score wins the round

The computer uses a basic strategy: it keeps rolling unless it is ahead of you, at which point it has a 50% chance of holding.

## Screenshots

| Start | Rolling | Result |
|:---:|:---:|:---:|
| ![Start](screenshots/Screenshot_1562769259.png) | ![Rolling](screenshots/Screenshot_1562769277.png) | ![Result](screenshots/Screenshot_1562769288.png) |

## 🛠 Tech Stack

| | Technology | Version |
|---|---|---|
| 📱 | Android SDK | 34 |
| ☕ | Java | 8 |
| 🏗️ | Gradle | 8.0 |
| 🧩 | AndroidX AppCompat | 1.6.1 |
| 📐 | ConstraintLayout | 2.1.4 |

## Getting Started

```bash
git clone https://github.com/stabgan/Scarne-Dice.git
```

Open the project in Android Studio (Flamingo or later) and run on an emulator or device with API 25+.

## Project Structure

```
app/src/main/
├── java/com/stabgan/scarnesdice/
│   └── MainActivity.java      # Game logic and UI
├── res/
│   ├── drawable/               # Dice face PNGs (1-6)
│   ├── layout/activity_main.xml
│   └── values/                 # Colors, strings, styles
└── AndroidManifest.xml
```

## ⚠️ Known Issues

- No persistent high-score or multi-round tracking (each round is standalone)
- Computer AI is simplistic (random hold when ahead)
- No landscape layout or tablet optimization

## License

This project was created as part of Google's Applied CS with Android curriculum.
