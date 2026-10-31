# Phone Trading Agent — Android starter 0.1.1

A native Android project scaffold for a phone-first trading-agent experiment.

## Build a debug APK
This repository includes a GitHub Actions workflow. On a push to `main`, or when manually run from the Actions tab, it attempts to build a debug APK and publish it as a downloadable workflow artifact.

1. Open the **Actions** tab.
2. Select **Build Android APK**.
3. Choose **Run workflow** if no build has started automatically.
4. Open the completed workflow run and download `PhoneTradingAgent-debug-apk`.
5. Extract the artifact and install `app-debug.apk` on the Android device.

## Current implementation
- Native Android dashboard shell.
- Local settings for TP, SL, trailing activation/distance, risk and allowed symbols.
- Foreground-service foundation with persistent notification.
- Accessibility-service foundation that observes supported MT5 window events only.
- Local configuration storage.
- GitHub Actions debug-APK build workflow.

## Important: this is not a trading agent yet
Not implemented or verified:
- OpenAI/API connection or API-key management.
- MT5 account, quote, or open-position reading.
- Buy, Sell, Close, or order modification.
- Broker-side SL/TP placement or trailing-stop management.
- Strategy engine, research ingestion, persistent AI memory, or performance analytics.
- Reliable 24/7 operation under Android battery/background restrictions.

The Activate button starts a local monitoring placeholder only. It does **not** activate trading. The accessibility service does not tap controls or place orders. Do not use this build for live or demo trading.

## Safety and privacy
- Do not add API keys, passwords, account numbers, or broker credentials to this public repository.
- No secrets are required to compile this starter.
- Any future order-execution feature must be developed and tested separately, with explicit user confirmation and risk controls.
