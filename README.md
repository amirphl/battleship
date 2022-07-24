# Battleship (multiplayer)
This is the implementation of the [Battleship game](https://en.wikipedia.org/wiki/Battleship_(game)) as part of the final project of Advanced Programming course in the university.  
The source codes is written in Java and utilizes socket programming, multithreading, and Swing.

## Demo
![demo](demo.gif)

### Features
- multiplayer
    - Make sure to allocated a **free** port for the server.
    - Open the port in firewall before establishing the server. i.e, Make sure that the server is accessible from client.
    - You can check it by ```netcat``` command line tool.
- chat service
    - Two players can send and receive messages.
    - History is saved.

### How to run?
super simple:
- Install JDK: ```sudo apt install openjdk-11-jdk```
- (optional) run: ```ldd /usr/lib/jvm/java-11-openjdk-amd64/lib/libawt_xawt.so```
- Compile the project through command line: ```javac -cp ./src/:./lib/* -d ./out/ ./src/ir/aut/test/head/Main.java```
    - ```./src/```: source files are here!
    - ```./lib/```: external libraries are here.
    - ```-d ./out/```: Specifies the output directory.
    - ```./src/ir/aut/test/head/Main.java```: This is the path to the Main class.
- Make sure that there exists a folder with name **./ConversationsHistory/** in the current directory. If doesn't exists, create one.
- Run the game: ```java -cp ./out/:./lib/* ir.aut.test.head.Main```

### TODO
- Resolve ExpectationJFrame while closing panel.
- Notify name of the player joined after ReceivedConnectionsFrame.
- Display name and IP of the players.
- Implement hotkeys.
- Down part of the ships jpanel can be improved.
- What happens if there exist no jpanel (I have deleted all panel or no one joined)?
- Which one to use? repaint or revalidate?
- Notify if the client got rejected by the server.
- Implement scroll bar.
- Handle incorrect IP.
- Write Java doc.
- Some implementation is missed from the last page of definition of project.
- Extract Jar file.
- OrderingJPanel, menuPanel
- ChatJPanel, file
- ShipsJpanel and leaveButton -----> destroy
- frame ---- > window listener ---- > setDiffaultClose
