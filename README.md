# Portfolio project IDATG2003
### Authors:

[@nheggoe](https://github.com/nheggoe)

[@Mihailohrani](https://github.com/Mihailohrani)

## Project description

This project is an academic exam project developed for the course IDATG2003 – Programming 2.
It is a JavaFX-based desktop application that implements a digital board game, designed to demonstrate principles of
modular software architecture, object-oriented programming, and user interface development in Java.

## Project structure

```
$ tree boardgame -d -L 2
boardgame
├── common
│   ├── event
│   ├── io
│   ├── repository
│   ├── ui
│   └── util
├── core
│   ├── model
│   └── ui
└── games
    ├── monopoly
    └── snake

13 directories
```

Project source code is organised into packages:

* `core` package provides the abstract class that concreate implementation extends from.
* `games` package is where the individual game lives.

## Data Files

The application uses external CSV files located in the `/data/csv/` directory to configure board elements and player
profiles.

- `player.csv` can be edited directly through the application’s built-in interface.
- `snakeAndLadder.csv` and `monopoly.csv` must be manually edited outside the application.

To modify the Snake and Ladder board, you may add or remove lines in `snakeAndLadder.csv`. Highest tested was 10,000; it
remained functional but rendered poorly and overlapped other UI elements.

To modify the Monopoly board, you may add or remove lines in `monopoly.csv`. Ensure the total number of lines is a multiple of 4 to maintain a square layout.

If anything goes wrong, simply delete the corresponding CSV file in the `/data/csv/` folder — a default version will be automatically regenerated on the next launch.

Ensure that the file structure and formatting are preserved when editing these files to avoid runtime errors.

## Link to repository

[GitHub Repository](https://github.com/nheggoe/board-game)

## How to run the project

This project requires [Java JDK 21](https://whichjdk.com/) to be installed on the system.
It is being developed and tested using the [Liberica Full JDK 21](https://bell-sw.com/libericajdk/).

Before running any commands, check if the correct version is installed on the system by typing:

```bash
java --version
```

If you have the correct java version installed, you may proceed to build the project from source.

1. Go to the project directory.

```bash
cd <project-root>
```

2. Run the following command in the terminal to build and run the project.

```bash
./mvnw -q
```

## References

### Observer Design Pattern

[Refactoring Guru](https://refactoring.guru/design-patterns/observer/java/example)
