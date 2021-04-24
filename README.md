Net Worth ADVANCE
===================
Created by : Davis Insua, Jakob Guadagno, Casey St Pierre, Jared Hernandez, Henry Thiel

Source Description 
--------------------
Net Worth Advance is a Java application used to calculate the user's net worth and
provides various ultilities. The GUI is written using the JavaFX library, graphing is
done through the JFreeChart library, and file I/O and various utilities use the base
Java libraries.

The program features an input form for the user to type their assets and liabilities
in various categories, along with a name for the record. These records are saved, and can
be loaded by the program at a later time. The records can be displayed as an over-time
graph, where the user's net worth increases and decreases can be tracked in graphical
form. For a more tabulated display of the user's records, all records can be displayed,
presenting all stored data to the user.

Build Instructions
-------------------
Building from a source ZIP and GitHub clone are functionally the same, however to make
sure that your IDE recognizes the project automatically, the correct root directory
must be selected.
- Install the latest `Java JDK`
- Install `JavaFX`
- Install a `Java IDE`
### From GitHub Clone:
- Download the latest source using the below command in `cmd`
```
git clone https://github.com/FPU-Spring-2021-CEN4010/group-project-group-2-s2
```
- Create a new project from existing files, using `group-project-group-2-s2` as the root
  folder. This folder should contain the `src` and `lib` files, and this file.
### From Source Download:
- Download latest source Zip (or zip source from assignment upload)
- Extract to some folder `folder-name`
- Create a new project from existing files, using `folder-name` as the root folder. This
  folder should contain the `src` and `lib` files, and this file.
### Continue:
- In the `Java Build Path` add the `JavaFX SDK` as a `Library` and the below JAR files as
  `JARs` to the build path:
  - `lib/jcommon-1.0.0.jar`
  - `lib/jfreechart-1.0.0.jar`
  - `lib/junit-3.8.jar`
- Create a new `Run Configuration` using the `MainWindow` main class and run

### Unit Testing
To enable unit testing, modify the above `Run Configuration`, adding the VM argument
`-ea` to enable assertions. If an assertion fails, use the console to investigate the
failed assertion. 

Code Organization
-------------------
All code is contained within two packages, `utility` and `windows`, with one file in the
default package. All packages are contained within the `src` folder. The `lib` folder
contains the `JFreeChart` JAR library files.
### Default Package
The default package contains only the MainWindow class.
#### MainWindow
This class is the root window, and is responsible for creating all other
windows and serves as the entry point for the program.
### Utility Package
The Utility package contains utility classes used elsewhere in the program.
#### PriceTableCell
This class is used by the RecordWindow class to create table cells with money formatting.
All credit goes to
[Stack Overflow](https://stackoverflow.com/questions/48552499/accounting-style-table-cell-in-javafx).
#### RecordTableEntry
This is a class used to wrap a `String` and a `Double` together in a way that is
compatable for a `JavaFX table view`.
#### UserRecord
This class represents the user's net worth record, and contains multiple methods for
loading records from a file, saving them back to a file, reading the file's contents, and
manages what kinds of fiscal entries can be added to the record. This class also performs
unit testing for its methods, which can be called using the instructions detailed in the
`Unit Testing` section.
### Windows Package
This package contains all classes that are directly seen by the user, with the exception
of the main window, which is controlled by the `MainWindow` class in the default package.
#### CalcFormWindow
This class creates a form to present to the user for them to insert their assets and
liabilities into. This window is accessed using the `Calculate Net Worth` button on the
main window.
#### GraphWindow
This class creates a window that draws a graph out of the user's saved records. The X
axis lists the names of the record, in their order of creation, while the Y axis displays
the Net Worth of the record. This window is access using the `Generate Graph` button on
the main window.
#### RecordWindow
The record window allows the user to view all of their saved records in an easy-to access
way. The user's records are displayed, with their name and date, in multiple pages.
Selecting a record opens a new window which displays all relevant data in the record.
This window is accessed using the `View Records` button on the main window.

Libraries
-------------------
The graphical library `JavaFX` was used for all of the GUI elements and handles user
input and window management. Another library, `JFreeChart`, is used to generate graphs
for the `GraphWindow` class. The class `PriceTableCell` was created by a 3rd party on
Stack Overflow. All other code was written by our team.
