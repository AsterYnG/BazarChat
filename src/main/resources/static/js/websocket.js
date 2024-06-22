const client = new StompJs.Client();
client.brokerURL = 'ws://localhost:8080/global';

client.onConnect = function (frame) {
    // Do something, all subscribes must be done is this callback
    // This is needed because this will be executed after a (re)connect
    client.subscribe('/topic/messages', callback);
};

client.onStompError = function (frame) {
    // Will be invoked in case of error encountered at Broker
    // Bad login/passcode typically will cause an error
    // Complaint brokers will set `message` header with a brief message. Body may contain details.
    // Compliant brokers will terminate the connection after any error
    console.log('Broker reported error: ' + frame.headers['message']);
    console.log('Additional details: ' + frame.body);
};

client.activate();

callback = function handleMessageFromServer(message){
    printMessage(box,JSON.parse(message.body));
}
