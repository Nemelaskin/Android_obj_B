package Utils

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState

class signalR : AppCompatActivity() {

    lateinit var hubConnection:HubConnection
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        hubConnection = HubConnectionBuilder.create("http://10.0.2.1:5000/chat").build()


        if(hubConnection.connectionState == HubConnectionState.DISCONNECTED){
            hubConnection.start();
        }
    }
}