package com.example.myapplication2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.example.myapplication2.netzwerk.BluetoothHandler;
import com.example.myapplication2.netzwerk.BluetoothHandlerImpl;
import com.example.myapplication2.netzwerk.ClientClass;
import com.example.myapplication2.netzwerk.SendReceive;
import com.example.myapplication2.netzwerk.ServerClass;

public class NetzwerkTests {

    private BluetoothAdapter mockBluetoothAdapter;
    private BluetoothHandlerImpl bluetoothHandler;
    private ServerClass mockServerClass;
    private ClientClass mockClientClass;
    private SendReceive mockSendReceive;
    private BluetoothSocket mockSocket;
    private InputStream mockInputStream;
    private OutputStream mockOutputStream;
    private SendReceive sendReceive;
    private BluetoothHandler mockHandler;

    @Before
    public void setUp() throws IOException {
        mockSocket = mock(BluetoothSocket.class);
        mockInputStream = mock(InputStream.class);
        mockOutputStream = mock(OutputStream.class);
        mockHandler = mock(BluetoothHandler.class);

        when(mockSocket.getInputStream()).thenReturn(mockInputStream);
        when(mockSocket.getOutputStream()).thenReturn(mockOutputStream);

        sendReceive = new SendReceive(mockSocket, mockHandler);

        mockBluetoothAdapter = mock(BluetoothAdapter.class);
        mockServerClass = mock(ServerClass.class);
        mockClientClass = mock(ClientClass.class);
        mockSendReceive = mock(SendReceive.class);

        bluetoothHandler = new BluetoothHandlerImpl(mockBluetoothAdapter);

        // Inject mock ServerClass, ClientClass, and SendReceive into the handler
        bluetoothHandler.setServerClass(mockServerClass);
        bluetoothHandler.setClientClass(mockClientClass);
        bluetoothHandler.setSendReceive(mockSendReceive);
    }

    @Test
    public void testWrite() throws IOException {
        byte[] data = "Test data".getBytes();
        sendReceive.write(data);

        verify(mockOutputStream, times(1)).write(data);
    }

    @Test
    public void testCancel() throws IOException {
        sendReceive.start();
        sendReceive.cancel();

        assertFalse(sendReceive.getRunning());
        verify(mockSocket, times(1)).close();
        verify(mockInputStream, times(1)).close();
        verify(mockOutputStream, times(1)).close();
    }

    @Test
    public void testGetMessageReceived() {
        byte[] testData = "Test data".getBytes();
        sendReceive.setMessageReceived(testData);

        byte[] receivedData = sendReceive.getMessageReceived();
        assertArrayEquals(testData, receivedData);
    }

    @Test
    public void testClearMessageReceived() {
        byte[] testData = "Test data".getBytes();
        sendReceive.setMessageReceived(testData);
        sendReceive.clearMessageReceived();

        assertNull(sendReceive.getMessageReceived());
    }

    @Test
    public void testRunMethod_receivesDataSuccessfully() throws InterruptedException, IOException {
        // Set up the mock InputStream to return "Test data"
        String testData = "Test data";
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(testData.getBytes());
        when(mockSocket.getInputStream()).thenReturn(testInputStream);

        // Restart the sendReceive instance with the new input stream
        sendReceive = new SendReceive(mockSocket, mockHandler);
        Thread thread = new Thread(sendReceive::run);
        thread.start();

        // Give the thread some time to read the data
        Thread.sleep(500);

        // Verify the data received
        byte[] receivedData = sendReceive.getMessageReceived();
        assertNotNull(receivedData);
        assertEquals(testData, new String(receivedData));

        // Clean up
        sendReceive.cancel();
        thread.join();
    }


    @Test
    public void testStoppConnection() throws IOException {
        // Call the method to be tested
        bluetoothHandler.stoppConnection();

        // Verify that the cancel method was called on each component
        verify(mockSendReceive, times(1)).cancel();
        verify(mockServerClass, times(1)).cancel();
        verify(mockClientClass, times(1)).cancel();

        // Verify that the components are set to null
        assertNull(bluetoothHandler.getSendReceive());
        assertNull(bluetoothHandler.getServerClass());
        assertNull(bluetoothHandler.getClientClass());
    }




}
