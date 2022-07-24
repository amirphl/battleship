# Battleship (multiplayer)
This is the implementation of the [Battleship game](https://en.wikipedia.org/wiki/Battleship_(game)) as part of the final project of the Advanced Programming course at the university.  
The source code is written in Java and utilizes socket programming, multithreading, and Swing.

## Demo
![demo](demo.gif)  
Please refer to the instructions in the `How to run?` section since these are a little bit changed!

### Features
- multiplayer
    - Make sure to allocate a **free** port for the server.
    - Open the port in the firewall before establishing the server. i.e, Make sure that the server is accessible from the client.
    - You can check it by ```netcat``` command line tool.
- chat service
    - Two players can send and receive messages.
    - History is saved.

### How to run?
super simple:
- Install JDK: ```sudo apt install openjdk-11-jdk```
- (optional) run: ```ldd /usr/lib/jvm/java-11-openjdk-amd64/lib/libawt_xawt.so```
- Compile the project through command line: ```javac -cp ./src/:./lib/* -d ./out/ ./src/ir/aut/main/head/Main.java```
    - ```./src/```: source files are here!
    - ```./lib/```: external libraries are here.
    - ```-d ./out/```: Specifies the output directory.
    - ```./src/ir/aut/main/head/Main.java```: This is the path to the Main class.
- Make sure that there exists a folder with the name **./ConversationsHistory/** in the current directory. If it doesn't exist, create one.
- Run the game: ```java -cp ./out/:./lib/* ir.aut.main.head.Main```

### TODO
- Resolve ExpectationJFrame while closing the panel.
- Notify the name of the player who joined after ReceivedConnectionsFrame.
- Display the name and IP of the players.
- Implement hotkeys.
- Down part of the ship's jpanel can be improved.
- What happens if there exists no jpanel (I have deleted the panel or no one joined.)?
- Which one to use? repaint or revalidate?
- Notify if the client got rejected by the server.
- Implement the scroll bar.
- Handle incorrect IP.
- Write Java doc.
- Some implementation is missed from the last page of the definition of the project.
- Extract the Jar file.
- OrderingJPanel, menuPanel
- ChatJPanel, file
- ShipsJpanel and leaveButton -----> destroy
- frame ---- > window listener ---- > setDiffaultClose
