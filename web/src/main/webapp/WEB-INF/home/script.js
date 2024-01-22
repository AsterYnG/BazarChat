function updateClock() {
    const now = new Date();
    const hours = now.getHours().toString().padStart(2, '0');
    const minutes = now.getMinutes().toString().padStart(2, '0');
    const seconds = now.getSeconds().toString().padStart(2, '0');

    document.getElementById('clockDisplay').innerText = hours + ":" + minutes + ":" + seconds;
}

async function getLastMessages() {
    let messages;
    let promise = await fetch("/chat");
    if (promise.ok) {
        messages = await promise.json();
        let box = document.getElementById("message-box");
        for (let i = 0; i < messages.length; i++) {
            printMessage(box,messages[i]);
        }
    } else window.alert("GG");

}

function printNewAuthor(box,message,lastMessage){
    if(lastMessage === null){
        let author = document.createElement("div");
        author.textContent = message.customer.login;
        box.lastElementChild.before(author);
        return;
    }
    if (message.customer.login !== lastMessage.author){
        let author = document.createElement("div");
        author.textContent = message.customer.login;
        box.lastElementChild.before(author);
    }
}
function printMessage(box,message){
    let div = document.createElement('div');
    if (!messageExists(message.messageId)){ // Print only new incoming messages
        let lastMessage = box.lastElementChild;
        div.className = 'message';
        div.messageId = message.messageId;
        div.author = message.customer.login;
        div.textContent = message.message;
        box.appendChild(div);
        printNewAuthor(box,message,lastMessage);
        box.scrollTop = box.scrollHeight;
    }
}

function messageExists(messageId) {
    let box = document.getElementById("message-box");
    let childNodes = box.childNodes;
    for (let node of childNodes) {
        if (node.messageId === messageId){
            return true;
        }
    }
    return false;
}

const messageForm = document.getElementById('messageForm');
if (messageForm){
    messageForm.addEventListener('submit',sendMessage);
}
else window.alert("pizdec");
async function sendData(data) {
    const formData = new URLSearchParams();
    for (const pair of data.entries()) {
        formData.append(pair[0], pair[1]);
    }

    return await fetch('/chat', {
        method: 'POST',
        headers:{
            'Content-Type': 'application/x-www-form-urlencoded'
        },

        body: formData.toString()
    })


}

async function sendMessage(event){


    event.preventDefault();
    let data = new FormData(event.target);
    let response = await sendData(data);
    if (!response.ok){
        window.alert("Form pushing error");
    }
    document.getElementById("messageInput").value = '';

   let box = document.getElementById('message-box');
}



// Обновляем часы каждую секунду
setInterval(updateClock, 1000);

setInterval(getLastMessages, 500);





