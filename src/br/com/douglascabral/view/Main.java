package br.com.douglascabral.view;

import br.com.douglascabral.androsockettest.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

public class Main extends Activity {
	
	private final WebSocketConnection mConnection = new WebSocketConnection();
	private Context context;
	private EditText txtMsg;
	private Button btnEnviarMsg; 
	
	
	/**
	 * Inicia a configuração do WebSocket
	 */
	private void start() {
		final String url = "ws://172.17.62.208:8080";
		
		try {
			mConnection.connect(url, new WebSocketHandler(){
				
				@Override
				public void onClose(int code, String reason) {
					// TODO Auto-generated method stub
					super.onClose(code, reason);
				}
				
				@Override
				public void onOpen() {
					// TODO Auto-generated method stub
					super.onOpen();
				}
				
				@Override
				public void onTextMessage(String payload) {
					// TODO Auto-generated method stub
					super.onTextMessage(payload);
					Toast.makeText(context, payload, Toast.LENGTH_LONG).show();
				}
				
			});
		} catch (WebSocketException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		context = getApplicationContext();
		
		txtMsg = (EditText) findViewById(R.id.txt_msg);
		
		btnEnviarMsg = (Button) findViewById(R.id.btn_enviar_msg);
		btnEnviarMsg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if ( mConnection != null && mConnection.isConnected() ) {
					mConnection.sendTextMessage( txtMsg.getText().toString() );
					txtMsg.setText("");
				} else { 
					Toast.makeText(context, "Conexão não realizada", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		start();
	}
}
