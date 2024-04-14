# Usercentrics Sample App

This sample app demonstrates how to integrate the Usercentrics API/SDK into an Android application. Usercentrics is a consent management platform that helps websites and apps to collect, manage, and document user consents in compliance with data privacy regulations such as GDPR and CCPA.

## Tech Stack

- *Single Activity Pattern:* The app follows the single activity pattern, which promotes a more modular and maintainable architecture by having a single activity manage multiple fragments.
  
- *Clean Architecture:* The app is structured using the Clean Architecture principles, separating concerns into layers such as presentation, domain, and data.
  
- *MVVM + UseCase + Repo + DataSource:* The app architecture is based on the Model-View-ViewModel (MVVM) pattern along with UseCases, Repositories, and DataSources to manage data flow and business logic.

- *Kotlin Coroutines + Flow:* Kotlin Coroutines are used for asynchronous programming and background tasking, while Flow is used for reactive streams of data transmission.

- *Navigation Graph:* Navigation graph is used to define the navigation flow between fragments within the app.

- *Custom Progress Bar:* A custom progress bar is implemented to provide visual feedback to users while asynchronous tasks are being executed in the background.

- *Unit Tests:* Unit tests are implemented using the MockK framework to ensure the correctness of individual components in isolation.

- *UI Tests:* UI tests are implemented using Espresso to verify the correctness of the app's user interface and user interactions.

## Functionality

The Usercentrics sample app demonstrates the following functionality:

- Displaying the consent banner to users.
- Collecting user consents for data processing.
- Calculating the cost of data processing based on the consents provided.

## How to Use

To use the Usercentrics sample app, follow these steps:

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Build and run the app on an Android device or emulator.
4. Explore the app's functionality and user interface.
5. Review the code to understand the integration of Usercentrics SDK and the app's architecture.


## Screen Shorts
![Screenshot_1](https://github.com/MALIKHUMZA81/UserCentrics/assets/77398922/fe398454-1121-486b-8edd-41fa94ceb265)
![Screenshot_2](https://github.com/MALIKHUMZA81/UserCentrics/assets/77398922/bfaafcdb-c694-4546-80b4-7ddb5ba21366)
![Screenshot_Logs](https://github.com/MALIKHUMZA81/UserCentrics/assets/77398922/3d6a4d1a-6b45-4927-b485-f56dd7bbb6d6)

