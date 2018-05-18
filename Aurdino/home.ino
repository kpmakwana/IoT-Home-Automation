#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>

// Set these to run example.
#define FIREBASE_HOST "YOUR PROJECT URL"
#define FIREBASE_AUTH "YOUR API"
#define WIFI_SSID "hotspot name"
#define WIFI_PASSWORD "password of hotspot"

void setup() {
  Serial.begin(115200);
pinMode(D2,OUTPUT); //buzzer
pinMode(D5,OUTPUT);  //bulb
pinMode(D6,OUTPUT);  //fan
  // connect to wifi.
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected: ");
  Serial.println(WiFi.localIP());
  
 Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
   Firebase.set("buzzer", 0);
   Firebase.set("bulb", 0);
   Firebase.set("fan", 0);
}

int n = 0,bulb=0,fan=0;

void loop() {
  
  // set value
   n=Firebase.getInt("buzzer");
  // handle error
  if (n==1) {
      Serial.print("BUZZ Play\n");
      digitalWrite(D2,HIGH); 
      delay(100);
  }
  else{
Serial.print("BUZZ OFF\n");
      digitalWrite(D2,LOW); 

  }
  bulb=Firebase.getInt("bulb");
  // handle error
  if (bulb==0) {
      Serial.print("bulb is on\n");
      digitalWrite(D5,HIGH); 
      delay(100);
  }
  else{
Serial.print("bulb is off\n");
      digitalWrite(D5,LOW); 

  }
  fan=Firebase.getInt("fan");
  // handle error
  if (fan==0) {
      Serial.print("fan is on\n");
      digitalWrite(D6,HIGH); 
      delay(100);
  }
  else{
Serial.print("bulb is off\n");
      digitalWrite(D6,LOW); 

  }
  if (Firebase.failed())  {

  Serial.print("setting /number failed:");
  
  Serial.println(Firebase.error());

  return;

}
  delay(200);
}
 
