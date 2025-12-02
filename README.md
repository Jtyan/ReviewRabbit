# ReviewRabbit
## Overview

This is an Android application that allows users to browse detailed reviews of companies and submit employee reviews about the company that they have worked for.

### Demo Video
[Demo Video](https://github.com/user-attachments/assets/02d073c8-6f03-49a7-9747-13d2a5bfdaab)

### Screenshots
![HomeScreen](https://github.com/user-attachments/assets/88acfb35-e0d4-4052-bda0-e34a30ce98fe) &nbsp;
![CompanyScreen](https://github.com/user-attachments/assets/fc73228f-9cef-4bf9-9c40-ff61a17b9e90) &nbsp;
![CompanyScreen](https://github.com/user-attachments/assets/43cf37c4-f47c-45c0-831e-a2c76266fc27) &nbsp;
![LoginScreen](https://github.com/user-attachments/assets/8f3ce2a2-c62e-4b7c-84bc-73693bf30d8b) &nbsp;
![UserSettings](https://github.com/user-attachments/assets/65513c0f-ffed-4cbf-9bf6-e7ced07ba595) &nbsp;
![AddNewCompany](https://github.com/user-attachments/assets/fe82d291-5913-4e11-887e-eb4bfbcd6d9e) &nbsp;

## Install and Run

1. Clone the repository:
````
git clone https://github.com/Jtyan/ReviewRabbit.git
````
2. Open the project in Android Studio.
   
3. Build and run the project on an emulator or your android device.

## Features

* **Full User Authentication**: A complete system for user signup, login, logout, and password management (forgot, reset, and change)
* **Dynamic Company Discovery**: A filterable list of companies that users can search by multiple categories including location, country, industry, and tags
* **Detailed Review Submission**: Users can contribute detailed reviews with multi-category star ratings (management, culture, salary, etc.) and a markdown-supported text body
* **On-Demand AI Summaries**: A feature to generate a concise summary of all existing reviews for a company

## Tech Stack

- **Kotlin** as the main programming language.
- **Jetpack Compose** for a fully declarative, single-activity UI architecture
- **MVVM Architecture** for a clean separation of concerns between UI and business logic
- **Hilt** for robust, lifecycle-aware dependency injection
- **Kotlin Coroutines & Flow** for managing asynchronous operations and reactive UI state (StateFlow)
- **Retrofit** & **OkHttp** for consuming the REST API, featuring a custom AuthInterceptor for automatic token handling
- **Kotlinx.serialization** for parsing JSON data
- **Jetpack DataStore** for persisting user login sessions and authentication tokens
- **Coil** for efficient image loading
