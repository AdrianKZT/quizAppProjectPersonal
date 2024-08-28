# Quiz-App-Personal-Project
An interactive quiz app developed using Kotlin and Firebase.


# Introduction
A Quiz Application where users can create accounts as teachers or students. For teachers you get to create quizzes with timed multiple choice question(MCQ) and for students you get to join quiz prepared by using the specific ID. 
Teachers and Students get to see the leaderboard with the score for each quizzes participated by the students.


# Features
* User Authentication
* Quiz Question loaded from Firebase
* Score tracking
* Leaderboard to display scores from each quiz


# Screenshots
## If the images do not appear, kindly click the link to view them on [Google Slides](https://docs.google.com/presentation/d/1jcxPmrJLf9d8LzZsYta_gyXqr026qTtSNzbk2hTiVVM/edit?usp=sharing).

<table>
  <tr>
    <td>
      <strong>Login Page</strong>
      <br/>
      <br/>
      1. Existing user can login using email and password
      <br/>
      2. New user can navigate to Register page by clicking <br/> the sign up now on the bottom right of the screen
    </td>
     <td>
       <strong>Register Page</strong>
       <br/>
       <br/>
       1. New User can register and user can choose the role <br/> as a student or teacher
       <br/>
       2. Existing user can navigate to Login Page by clicking <br/> the sign in now on the bottom right of the screen
     </td>
  </tr>
  <tr>
    <td><img src="https://github.com/AdrianKZT/Quiz-App-Personal-Project/assets/92383323/99171c43-de9f-4e59-8e03-0880fbe3ec7e" width="150" height="300"></td>
    <td><img src="https://github.com/AdrianKZT/Quiz-App-Personal-Project/assets/92383323/af09d4f1-9780-4208-bfcd-f95e54c70a21" width="150" height="300"></td>
  </tr>
 </table>


### Teachers
 <table>
  <tr>
    <td>
      <strong>Home Page</strong>
       <br/>
      <br/>
      1. Teacher can change their profile pic and logout 
    </td>
     <td>
       <strong>Add Quiz Page</strong>
        <br/>
        <br/>
        1. Teacher can add quiz by creating the quiz and import CSV <br/> files and set the time for the quiz
     </td>
  </tr>
  <tr>
    <td><img src="https://github.com/AdrianKZT/Quiz-App-Personal-Project/assets/92383323/f61c87a3-ce34-4a44-8860-faed1f459512" width="150" height="300"></td>
    <td><img src="https://github.com/AdrianKZT/Quiz-App-Personal-Project/assets/92383323/a3938ea3-df56-4bd3-bef8-f0ce2cf64b18" width="150" height="300"></td>
  </tr>
 </table>


 ### Students
 <table>
  <tr>
    <td>
      <strong>Home Page</strong>
       <br/>
        <br/>
        1. Student can change their profile pic and logout
        <br/>
        2. Student can join the quiz by inserting the Quiz ID
    </td>
     <td>
       <strong>Quiz Home Page</strong>
        <br/>
        <br/>
        1. Students can see the Quiz ID, Title and the <br/> time limit for quiz
     </td>
    <td>
      <strong>Question Page</strong>
      <br/>
      <br/>
      1. Students will answer the question before the time runs out
    </td>
  </tr>
  <tr>
    <td><img src="https://github.com/AdrianKZT/Quiz-App-Personal-Project/assets/92383323/9a177871-123e-4e1d-8c9b-540ea30b8751" width="150" height="300"></td>
    <td><img src="https://github.com/AdrianKZT/Quiz-App-Personal-Project/assets/92383323/12e5b766-1a8a-4156-87ef-ab500ead0a86" width="150" height="300"></td>
    <td><img src="https://github.com/AdrianKZT/Quiz-App-Personal-Project/assets/92383323/435b4562-55a8-4bf1-8125-5c0696fcbcea" width="150" height="300"></td>
  </tr>
 </table>


### Leaderboard
<table>
  <tr>
    <td>
      <strong>Leaderboard</strong>
      <br>
      <br>
      1.Both student and teacher can see the leaderboard <br/>on which student score the most for each quiz 
    </td>
  </tr>
  <tr>
    <td>
      <img src="https://github.com/AdrianKZT/Quiz-App-Personal-Project/assets/92383323/c2ebc767-f5bc-478c-a419-ce99feed0ed6" width="150" height="300">
    </td>
  </tr>
</table>


# Prerequisites
Before you begin, ensure you have met the following requirements:

* Android Studio Installed
* Firebase Project Set Up

# Firebase Configuration
To use Firebase services, follow these steps:

1. Create a new Firebase project.
2. Add your Android app to the Firebase project.
3. Download the google-services.json file and place it in the app directory of your project.
   
For more detailed instructions, refer to the [Firebase documentation](https://firebase.google.com/docs/android/setup).
