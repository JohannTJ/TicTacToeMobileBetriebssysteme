package com.example.myapplication2;


import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.graphics.Color;
import android.widget.ArrayAdapter;

import com.example.myapplication2.R;
import com.example.myapplication2.View.bluetooth.BluetoothFragment;
import com.example.myapplication2.View.spiel.SpielFragment;
import com.example.myapplication2.control.SpielcontrollerImpl;
import com.example.myapplication2.model.TicTacToeModel;
import com.example.myapplication2.netzwerk.BluetoothHandlerImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.nio.ByteBuffer;

public class ControllerTests {
    private SpielcontrollerImpl controller;
    private TicTacToeModel mockDaten;
    private SpielFragment mockSpielFragment;
    private BluetoothFragment mockBluetoothFragment;
    private BluetoothHandlerImpl mockBluetoothHandler;


    @Before
    public void setUp() {
        mockDaten = mock(TicTacToeModel.class);
        mockSpielFragment = mock(SpielFragment.class);
        mockBluetoothFragment = mock(BluetoothFragment.class);
        mockBluetoothHandler = mock(BluetoothHandlerImpl.class);

        controller = new SpielcontrollerImpl(mockSpielFragment, mockDaten);
        controller.setbluetoothHandler(mockBluetoothHandler);
        controller.setBluetoothFragment(mockBluetoothFragment);

    }


    // handleSpielbuttonOnClickMethode()
    @Test
    public void testHandleSpielbuttonOnClickMethode_SinglePlayer_ValidMove() {
        // Set up conditions
        when(mockDaten.getMyturn()).thenReturn(true);
        when(mockDaten.getMehrspieler()).thenReturn(false);
        when(mockDaten.getButtonStatus(0)).thenReturn(0);
        when(mockDaten.getEntschieden()).thenReturn(false);
        when(mockDaten.naechstesFeld()).thenReturn(1);


        controller.handleSpielbuttonOnClickMethode(0);

        // Verify the interactions
        verify(mockDaten).setButton(0, 1);
        verify(mockSpielFragment).paintButton(0, Color.BLUE);
        verify(mockDaten).naechstesFeld();
        verify(mockDaten).setButton(1, 2);
        verify(mockSpielFragment).paintButton(1, Color.RED);
    }

    @Test
    public void testHandleSpielbuttonOnClickMethode_SinglePlayer_InvalidMove() {
        // Set up conditions
        when(mockDaten.getMyturn()).thenReturn(true);
        when(mockDaten.getMehrspieler()).thenReturn(false);
        when(mockDaten.getButtonStatus(0)).thenReturn(1);  // Field already taken

        // Call the method
        controller.handleSpielbuttonOnClickMethode(0);

        // Verify no interactions with mockDaten or mockSpielFragment for invalid move
        verify(mockDaten, never()).setButton(anyInt(), anyInt());
        verify(mockSpielFragment, never()).paintButton(anyInt(), anyInt());
    }

    @Test
    public void testHandleSpielbuttonOnClickMethode_SinglePlayer_NotMyTurn() {
        // Set up conditions
        when(mockDaten.getMyturn()).thenReturn(false);
        when(mockDaten.getMehrspieler()).thenReturn(false);
        when(mockDaten.getButtonStatus(0)).thenReturn(0);

        // Call the method
        controller.handleSpielbuttonOnClickMethode(0);

        // Verify no interactions with mockDaten or mockSpielFragment when not player's turn
        verify(mockDaten, never()).setButton(anyInt(), anyInt());
        verify(mockSpielFragment, never()).paintButton(anyInt(), anyInt());
    }

    @Test
    public void testHandleSpielbuttonOnClickMethode_MultiPlayer_NotMyTurn() {
        // Set up conditions
        when(mockDaten.getMyturn()).thenReturn(false);
        when(mockDaten.getMehrspieler()).thenReturn(true);
        when(mockDaten.getButtonStatus(0)).thenReturn(0);

        // Call the method
        controller.handleSpielbuttonOnClickMethode(0);

        // Verify no interactions with mockDaten, mockSpielFragment, or mockBluetoothHandler when not player's turn
        verify(mockDaten, never()).setButton(anyInt(), anyInt());
        verify(mockSpielFragment, never()).paintButton(anyInt(), anyInt());
        verify(mockBluetoothHandler, never()).sendMessage(any(byte[].class));
    }





    // sendMessageToOpponent()
    @Test
    public void testSendMessageToOpponent() {
        int message = 5;

        // Call the method
        controller.sendMessageToOpponent(message);

        // Capture the argument passed to sendMessage
        ArgumentCaptor<byte[]> argumentCaptor = ArgumentCaptor.forClass(byte[].class);
        verify(mockBluetoothHandler).sendMessage(argumentCaptor.capture());

        // Convert the captured byte array back to an integer
        ByteBuffer buffer = ByteBuffer.wrap(argumentCaptor.getValue());
        int sentMessage = buffer.getInt();

        // Assert that the sent message is correct
        assertEquals(message, sentMessage);
    }

    @Test
    public void testSendMessageToOpponent_Zero() {
        int message = 0;

        // Call the method
        controller.sendMessageToOpponent(message);

        // Capture the argument passed to sendMessage
        ArgumentCaptor<byte[]> argumentCaptor = ArgumentCaptor.forClass(byte[].class);
        verify(mockBluetoothHandler).sendMessage(argumentCaptor.capture());

        // Convert the captured byte array back to an integer
        ByteBuffer buffer = ByteBuffer.wrap(argumentCaptor.getValue());
        int sentMessage = buffer.getInt();

        // Assert that the sent message is correct
        assertEquals(message, sentMessage);
    }

    @Test
    public void testSendMessageToOpponent_Negative() {
        int message = -1;

        // Call the method
        controller.sendMessageToOpponent(message);

        // Capture the argument passed to sendMessage
        ArgumentCaptor<byte[]> argumentCaptor = ArgumentCaptor.forClass(byte[].class);
        verify(mockBluetoothHandler).sendMessage(argumentCaptor.capture());

        // Convert the captured byte array back to an integer
        ByteBuffer buffer = ByteBuffer.wrap(argumentCaptor.getValue());
        int sentMessage = buffer.getInt();

        // Assert that the sent message is correct
        assertEquals(message, sentMessage);
    }

    // handleBluetoothDaten()

    @Test
    public void testHandleBluetoothDatenValidMove() {
        // Simulate a valid message with move
        byte[] msg = ByteBuffer.allocate(4).putInt(5).array(); // Simulating move at position 5

        controller.handleBluetoothDaten(msg);

        // Verify that daten.setButton() and spielFragment.paintButton() were called correctly
        verify(mockSpielFragment).paintButton(5, Color.RED);
        verify(mockBluetoothHandler, never()).stoppConnection(); // Ensure connection not stopped
        // Add more verification as needed based on your game logic
    }

    @Test
    public void testHandleBluetoothDatenSurrender() {
        // Simulate a surrender message (intValue == 20)
        byte[] msg = ByteBuffer.allocate(4).putInt(20).array();

        controller.handleBluetoothDaten(msg);

        // Verify that handleSurrender() was called and appropriate actions were taken
        verify(mockSpielFragment).showToast(); // Verify that showToast() was called
        verify(mockBluetoothHandler).stoppConnection(); // Verify that connection was stopped
        // Verify other necessary behaviors after surrender
    }
    @Test
    public void testHandleBluetoothDaten_InvalidMessage() {
        // Mock data
        byte[] invalidMessageBytes = {1, 2, 3};

        // Mock behavior
        when(mockBluetoothHandler.getRunning()).thenReturn(true);
        when(mockBluetoothHandler.messageReceived()).thenReturn(invalidMessageBytes);

        // Call the method under test
        controller.handleBluetoothDaten(invalidMessageBytes);

        // Verify interactions
        verify(mockDaten, never()).setButton(anyInt(), anyInt());
        verify(mockSpielFragment, never()).paintButton(anyInt(), anyInt());
        verify(mockBluetoothHandler, never()).setRunning(false);
    }




    @Test
    public void testHandleDevicesButton() {
        // Mocken der Rückgabe des BluetoothHandlers
        ArrayAdapter<String> mockArrayAdapter = mock(ArrayAdapter.class);
        when(mockBluetoothHandler.handleDevicesButton(mockBluetoothFragment)).thenReturn(mockArrayAdapter);

        // Aufruf der zu testenden Methode
        ArrayAdapter<String> result = controller.handleDevicesButton();

        // Überprüfen, ob die Methode setStatus des BluetoothFragments aufgerufen wurde
        verify(mockBluetoothFragment).setStatus("Devices you connected earlier");

        // Überprüfen, ob die Methode handleDevicesButton des BluetoothHandlers mit dem richtigen Argument aufgerufen wurde
        verify(mockBluetoothHandler).handleDevicesButton(mockBluetoothFragment);

        // Überprüfen, ob das erwartete ArrayAdapter-Objekt zurückgegeben wurde
        assertEquals(mockArrayAdapter, result);
    }

    // handleListenButton()
    @Test
    public void testHandleListenButton() {
        // This method is not implemented, so no test is required at the moment
        // Add implementation and corresponding test when the method is implemented
    }


    // handleAbbruchbuttonOnClickMethode()
    @Test
    public void testHandleAbbruchbuttonOnClickMethodeMehrspieler() throws InterruptedException {
        // Set up conditions
        when(mockDaten.getMehrspieler()).thenReturn(true);

        // Call the method
        controller.handleAbbruchbuttonOnClickMethode();

        // Verify interactions
        verify(mockBluetoothHandler).sendMessage(ByteBuffer.allocate(4).putInt(20).array());
        verify(mockBluetoothHandler).stoppConnection();
        verify(mockSpielFragment).changeToFirstFragment();
    }

    @Test
    public void testHandleAbbruchbuttonOnClickMethodeEinzelspieler() throws InterruptedException {
        // Set up conditions
        when(mockDaten.getMehrspieler()).thenReturn(false);

        // Call the method
        controller.handleAbbruchbuttonOnClickMethode();

        // Verify interactions
        verify(mockBluetoothHandler,never()).sendMessage(ByteBuffer.allocate(4).putInt(20).array());
        verify(mockBluetoothHandler,never()).stoppConnection();
        verify(mockSpielFragment).changeToFirstFragment();
    }


    @Test
    public void testListViewClick() throws Exception {
        // Set up the mock behavior
        when(mockBluetoothHandler.listViewClick(anyInt(), eq(mockBluetoothFragment))).thenReturn("Connection Failed");

        // Call the method
        controller.listViewClick(0);

        // Wait for the background thread to complete
        Thread.sleep(100);

        // Verify interactions
        verify(mockBluetoothFragment).setStatus("Connecting");
        verify(mockBluetoothHandler).listViewClick(0,mockBluetoothFragment);
    }





    // checkBTState()

    @Test
    public void testCheckBTState_BluetoothNotSupported() {
        // Mocking
        when(mockBluetoothHandler.checkBTState()).thenReturn("1");

        // Aufruf der zu testenden Methode
        controller.checkBTState();

        // Überprüfen, ob die Methode errorExit des BluetoothFragments mit den richtigen Argumenten aufgerufen wurde
        verify(mockBluetoothFragment).errorExit("Error", "Bluetooth not support");
    }

    @Test
    public void testCheckBTState_BluetoothOn() {
        // Mocking
        when(mockBluetoothHandler.checkBTState()).thenReturn("2");

        // Aufruf der zu testenden Methode
        controller.checkBTState();

        // Überprüfen, ob die Methode showToast des BluetoothFragments mit dem richtigen Argument aufgerufen wurde
        verify(mockBluetoothFragment).showToast("...Bluetooth is ON...");
    }

    @Test
    public void testCheckBTState_BluetoothOff() {
        // Mocking
        when(mockBluetoothHandler.checkBTState()).thenReturn("3");

        // Aufruf der zu testenden Methode
        controller.checkBTState();

        // Überprüfen, ob die Methode showToast des BluetoothFragments mit dem richtigen Argument aufgerufen wurde
        verify(mockBluetoothFragment).showToast("...Please Turn Bluetooth ON...");
    }

}

