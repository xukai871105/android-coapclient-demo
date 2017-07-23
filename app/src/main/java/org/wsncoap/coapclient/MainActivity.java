package org.wsncoap.coapclient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;

public class MainActivity extends AppCompatActivity {

    private Button mGetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGetButton = (Button)findViewById(R.id.buttonGet);
        mGetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String uri = ((EditText)findViewById(R.id.editUri)).getText().toString();
                // new CoapGetTask().execute("coap://wsncoap.org");
                new CoapGetTask().execute(uri);
            }
        });
    }

    class CoapGetTask extends AsyncTask<String, String, CoapResponse> {

        protected void onPreExecute() {
            // reset text fields
            ((TextView)findViewById(R.id.textCode)).setText("");
            ((TextView)findViewById(R.id.textCodeName)).setText("Loading...");
            ((TextView)findViewById(R.id.textRtt)).setText("");
            ((TextView)findViewById(R.id.textContent)).setText("");
        }

        protected CoapResponse doInBackground(String... args) {
            CoapClient client = new CoapClient(args[0]);
            return client.get();
        }

        protected void onPostExecute(CoapResponse response) {
            if (response!=null) {
                ((TextView)findViewById(R.id.textCode)).setText(response.getCode().toString());
                ((TextView)findViewById(R.id.textCodeName)).setText(response.getCode().name());
                ((TextView)findViewById(R.id.textRtt)).setText(response.advanced().getRTT()+" ms");
                ((TextView)findViewById(R.id.textContent)).setText(response.getResponseText());
            } else {
                ((TextView)findViewById(R.id.textCodeName)).setText("No response");
            }
        }
    }
}
