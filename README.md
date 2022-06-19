## KOFK Archive File Manager

This is a GUI Program designed to make it easier to add and modify listings on KOFKArchive.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

## File Structure

 - `Main.java`: Controls the entire program and is the central controller.
 - `MainMenuPanel.java`: Panel which handles all of the MainMenu panel functionalities (directing to different panels)
 - `NewListing.java`: Panel which handles all of the New Listing panel functionalities (creates new listing).
 - `DatabaseHandler.java`: Reads, Writes and appends data with the files in the KOFKArchive directory

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
