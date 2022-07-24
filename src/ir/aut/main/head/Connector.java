package ir.aut.main.head;

import ir.aut.main.logic.MessageManager;
import ir.aut.main.view.first.RCFCallBack;
import ir.aut.main.view.first.WaitingJFrameCallBack;
import ir.aut.main.view.second.ChatBoxCallBack;
import ir.aut.main.view.second.FrameCallBack;
import ir.aut.main.view.second.ShipsJPanelCallBack;

import static ir.aut.main.view.Constants.CLIENT;
import static ir.aut.main.view.Constants.SERVER;

/**
 * Created by Yana on 05/07/2017.
 */
public class Connector {

    private MessageManager messageManager;

    private FrameCallBack frameCallBack;
    private ShipsJPanelCallBack shipsJPanelCallBack;
    private ChatBoxCallBack chatBoxCallBack;
    private RCFCallBack rcfCallBack;
    private WaitingJFrameCallBack waitingJFrameCallBack;

    private String ip;
    private String playerName;
    private String opponentName;
    private int i;
    private String station;
    private int condition;
    private int mX;
    private int mY;
    private int terminateCondition;
    private boolean readinessCondition;
    private String conversationMessage;

    public Connector(MessageManager mM) {
        messageManager = mM;
    }

    public void setFrameCallBack(FrameCallBack fcb) {
        frameCallBack = fcb;
    }

    public void setShipsJPanelCallBack(ShipsJPanelCallBack sjcb) {
        shipsJPanelCallBack = sjcb;
    }

    public void setChatBoxCallBack(ChatBoxCallBack cbcb) {
        chatBoxCallBack = cbcb;
    }

    public void setRcfCallBack(RCFCallBack rcfCb) {
        rcfCallBack = rcfCb;
    }

    public void setWaitingJFrameCallBack(WaitingJFrameCallBack wjcb) {
        waitingJFrameCallBack = wjcb;
    }

    public void sendMessage(String message) {
        switch (message) {
            case "LeaveBeforeAccept":
                leaveBeforeAccept();
                return;
            case "SendClientIpToServer":
                sendClientIpToServer();
                return;
            case "Accept":
                accept();
                return;
            case "Reject":
                reject();
                return;
            case "DeleteSubJPanelByIP":
                deleteSubJPanelByIP();
                return;
            case "AddSubJPanel":
                addSubJPanel();
                return;
            case "ConsumeRejectMessage":
                consumeRejectMessage();
                return;
            case "ConsumeAcceptMessage":
                consumeAcceptMessage();
                return;
            case "SendMyName":
                sendMyName();
                return;
            case "ConsumeOpponentName":
                consumeOpponentName();
                return;
            case "SendLocation":
                sendLocation();
                return;
            case "ConsumeLocationMessage":
                consumeLocationMessage();
                return;
            case "SendTerminateMessage":
                sendTerminateMessage();
                return;
            case "ConsumeTerminateMessage":
                consumeTerminateMessage();
                return;
            case "CloseConnection":
                closeConnection();
                return;
            case "SendReadinessCondition":
                sendReadinessCondition();
                return;
            case "ConsumeReadinessCondition":
                consumeReadinessCondition();
                return;
            case "SendConversationMessage":
                sendConversationMessage();
                return;
            case "ConsumeConversationMessage":
                consumeConversationMessage();
                return;
        }
        System.out.println("Error in sending Message : " + message);
    }

    /**
     * user(client)
     */
    private void leaveBeforeAccept() {
        messageManager.sendRequestLeave(ip);
    }

    /**
     * user(client)
     */
    private void sendClientIpToServer() {
        messageManager.sendIP(playerName, ip);
    }

    /**
     * user(server)
     */
    private void accept() {
        messageManager.acceptRequest(i);
    }

    /**
     * user(server)
     */
    private void reject() {
        messageManager.rejectRequest(i);
    }

    /**
     * messageManager(to server)
     */
    private void deleteSubJPanelByIP() {
        rcfCallBack.deleteSubJPanel(i);
    }

    /**
     * messageManager(to server)
     */
    private void addSubJPanel() {
        rcfCallBack.addSubJPanel(opponentName, ip);
    }

    private void consumeRejectMessage() {
        waitingJFrameCallBack.close(0);
    }

    private void consumeAcceptMessage() {
        waitingJFrameCallBack.close(1);
    }

    /**
     * server  to client
     */
    private void sendMyName() {
        messageManager.sendMyName(playerName);
    }

    private void consumeOpponentName() {
        frameCallBack.setOpponentName(opponentName);
    }

    private void sendLocation() {
        messageManager.sendLocation(mX, mY, condition);
    }

    private void consumeLocationMessage() {
        if (condition == 3)
            frameCallBack.destroyMyShips(mX, mY);
        else
            frameCallBack.destroyOpponentShips(mX, mY, condition);
    }

    private void sendTerminateMessage() {
        messageManager.sendTerminate(terminateCondition);
    }

    private void consumeTerminateMessage() {
        if (terminateCondition == 1)
            frameCallBack.loose();
        else
            frameCallBack.win(terminateCondition);
    }

    private void closeConnection() {
        messageManager.onSocketClosed();

    }

    private void sendReadinessCondition() {
        messageManager.sendReadinessCondition(readinessCondition);
    }

    private void consumeReadinessCondition() {
        shipsJPanelCallBack.setOpponentReadiness(readinessCondition);
    }

    private void sendConversationMessage() {
        messageManager.sendConversation(conversationMessage);

    }

    private void consumeConversationMessage() {
        chatBoxCallBack.addText(conversationMessage, 2);
    }

    public synchronized void setIp(String ip) {
        this.ip = ip;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public synchronized void setI(int i) {
        this.i = i;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public void setmX(int x) {
        mX = x;
    }

    public void setmY(int y) {
        mY = y;
    }

    public void setTerminateCondition(int t) {
        terminateCondition = t;
    }

    public void setReadinessCondition(boolean r) {
        readinessCondition = r;
    }

    public void setConversationMessage(String c) {
        conversationMessage = c;
    }
}
