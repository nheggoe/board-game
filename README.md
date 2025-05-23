# Portfolio project IDATG2003

STUDENT NAME = Nick Heggø

STUDENT ID = 134132

STUDENT NAME = Mihailo Hranisavljevic

STUDENT ID = 123877

## Project description

This project is an academic exam project developed for the course IDATG2003 – Programming 2.
It is a JavaFX-based desktop application that implements a digital board game, designed to demonstrate principles of
modular software architecture, object-oriented programming, and user interface development in Java.

## Project structure

```aiignore
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

Project source code is organized into packages:

* `core` package provides the abstract class that concreate implementation extends from.
* `games` package is where the individual game lives.

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
