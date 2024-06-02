

async function getLastMessages() {
    let messages;
    let promise = await fetch("/api/v1/messages");
    if (promise.ok) {
        messages = await promise.json();
        let box = document.getElementById("message-box");
        for (let i = 0; i < messages.length; i++) {
            printMessage(box, messages[i]);
        }
    } else window.alert("GG");

}

function printNewAuthor(pContent, message, lastMessage, messageBox) {
    let pAuthor = document.createElement("p");
    let image = document.createElement("img");
    let authorContainer = document.createElement("div");
    image.src = message.imagePath;
    pAuthor.textContent = message.nickname;
    pAuthor.className = 'message-author';
    image.className = "author-pic";

    let userLogin = sessionStorage.getItem("userLogin")
    if (message.login === userLogin) {
        authorContainer.className = "author-container self"
        pAuthor.className = "message-author self"
        authorContainer.append(pAuthor);
        authorContainer.append(image);
    }
    else {
        pAuthor.className = "message-author"
        authorContainer.className = "author-container"
        authorContainer.append(image);
        authorContainer.append(pAuthor);
    }

    if (lastMessage === null) {
        messageBox.append(authorContainer);
        return;
    }

    if (lastMessage.login !== message.login) {
        messageBox.append(authorContainer);
    }
}

function printMessage(box, message) {
    let divMessage = document.createElement('div');
    let pContent = document.createElement('p');
    let pDate = document.createElement('p');
    let userLogin = sessionStorage.getItem("userLogin")
    let messageContainer = document.createElement("div");

    messageContainer.className = 'message-container';
    pContent.className = 'message-content';
    pDate.className = 'message-date';

    if (message.login === userLogin) {
        messageContainer.className = 'message-container self';
        divMessage.className = "message self";
    } else{
        messageContainer.className = 'message-container';
        divMessage.className = "message";
    }

    if (!messageExists(message.messageId)) { // Print only new incoming messages
        let lastMessage = box.lastElementChild;
        let date = new Date(message.date);

        messageContainer.messageId = message.messageId;
        messageContainer.login = message.login;
        pContent.textContent = message.message;
        pDate.textContent = date.toLocaleTimeString('ru-RU');


        box.appendChild(messageContainer);
        printNewAuthor(pContent, message, lastMessage, messageContainer);
        messageContainer.append(divMessage);
        divMessage.append(pContent, pDate)
        box.scrollTop = box.scrollHeight;
    }
}


function messageExists(messageId) {
    let box = document.getElementById("message-box");
    let childNodes = box.childNodes;
    for (let node of childNodes) {
        if (node.messageId === messageId) {
            return true;
        }
    }
    return false;
}

const messageForm = document.getElementById('messageForm');
if (messageForm) {
    messageForm.addEventListener('submit', sendMessage);
} else window.alert("pizdec");

async function sendData(data) {
    return await fetch('/api/v1/messages', {
        method: 'POST',
        body: data
    })
}


async function sendMessage(event) {
    let userId = sessionStorage.getItem("userId")

    event.preventDefault();
    let data = new FormData(event.target);
    console.log(sessionStorage.getItem("userId"));
    data.append('userId', userId)

    const inputField = document.getElementById('messageInput');

    // Проверка корректности данных (н
    // апример, не пустая строка)
    if (inputField.value.trim() === '') {
        // Показываем всплывающее окно

    } else {
        let response = await sendData(data);
        if (!response.ok) {
            window.alert("Form pushing error");
        }

        let box = document.getElementById('messageInput');
        box.value = '';
    }
}
setInterval(getLastMessages, 500);
