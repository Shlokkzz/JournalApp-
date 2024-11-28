# Journal App

## Student Details

- **Student 1:** Shlok Patel  
  **ID:** 2021A7PS2441G

- **Student 2:** Suraz Kumar  
  **ID:** 2021B2A71602G

---

## Project Description

The **Journal App** is an Android application designed to allow users to record their daily activities, including the date, start time, and end time for each entry. Users can navigate between journal entries, edit them, delete them, and share them via text or email. The app includes accessibility enhancements and is tested for robustness.

### Known Bugs
- Currently, there are no known bugs.

---

## Task Summary

### 1. **Navigation with Nav Graph Actions**
- Implemented navigation between fragments using safe args.
- Data is passed between fragments securely and efficiently.
- Test cases were created to verify proper navigation.

### 2. **Database Schema Modifications**
- Updated the database schema to include columns for `date`, `start time`, and `end time`.
- Removed `duration` from the schema as it can now be computed dynamically.
- Implemented `DELETE` functionality in the database and verified with unit tests.

### 3. **Delete Button**
- Added a `DELETE` button to the `EntryDetailsFragment` menu.
- Clicking the button triggers a confirmation dialog before deleting an entry.
- The entry is removed from the database only upon user confirmation.

### 4. **Share Button**
- Added a `SHARE` button to the `EntryDetailsFragment` menu.
- This button creates a plain text message in the format:  
  `"Look what I have been up to: <title> on <date>, <start-time> to <end-time>"`
- Users can share the message via compatible apps (e.g., SMS, email).

### 5. **Info Button**
- Added an `INFO` button to the `EntryListFragment` menu.
- Clicking the button opens the webpage of the book *Atomic Habits* by James Clear.

### 6. **Accessibility Enhancements**
- Ran the app using TalkBack and the Accessibility Scanner.
- Enhanced labels and descriptions for better user experience.
- Added five Espresso tests to verify accessibility compliance.

---

## Additional Details

- The app's state persists across screen rotations and process deaths.
- Used Git best practices throughout the project.

### Optional Enhancements
- Added suggestions for enhancements, such as:
    - Graphical insights into user activity trends.
    - Master-detail views for large-screen devices.

---

## Approximate Hours Worked

- **Total Time:** ~20 hours

---

## Pair programming score 4 out of 5

---

## Assignment Rating

- **Difficulty:** 8/10

---

## Attributions

- Referenced Android developer documentation for database and UI testing.
- Guidance from class discussions on Room database and navigation patterns.

---

## Testing and Stability

- Espresso tests were used extensively for navigation and accessibility validation.
- The app has been run through the monkey tool without any crashes.
