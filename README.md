# ASTROFEED 🚀

**ASTROFEED** is an Android application that provides up-to-date information on upcoming rocket launches. The app utilizes the [SpaceX unofficial API](https://github.com/r-spacex/SpaceX-API) and the [Launch Library](https://thespacedevs.com/llapi) to deliver the latest news, stats, and launch details about space missions worldwide.

## Features
- 📰 **News Feed**: Stay informed with the latest news about space missions, rocket launches, and space exploration.
- 📃 **Reddit Feed**: Keep up to date with social media posts across multiple space related subreddits
- 🚀 **Upcoming Launches**: View detailed information on upcoming rocket launches, including mission details, launch date, and rocket stats.
- 🌍 **Global Coverage**: Provides data on launches happening around the globe.

## Tech Stack
- **Kotlin**: Android app development
- **SpaceX API**: Provides real-time information on SpaceX missions.
- **Launch Library API**: Delivers comprehensive data about launches from various space agencies.
- **Retrofit**: For network operations.
- **Jetpack Components**: Includes Navigation, ViewModel, and LiveData.
- **Material Design 3**: Modern UI design for the best user experience.

## Screenshots
Stay tuned! App screenshots are slingshotting around the moon!

## Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/rocket-launch-explorer.git
    ```
2. Open the project in Android Studio.
3. Build and run the project on your Android device or emulator.

## API Keys Setup
To use the app, you will need API keys for the SpaceX API and Launch Library.

1. **SpaceX API**: No API key required.
2. **Launch Library API**: Register on [Launch Library API](https://thespacedevs.com/llapi) to get an API key and add it to the `local.properties` file:
    ```bash
    LL_API_KEY=your_api_key_here
    ```

## Contributing
Contributions are welcome! If you'd like to contribute, feel free to submit a pull request or open an issue.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Credits
- **SpaceX API** by [r-spacex](https://github.com/r-spacex/SpaceX-API)
- **Launch Library API** by [The Space Devs](https://thespacedevs.com/llapi)
