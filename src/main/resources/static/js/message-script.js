const box = document.getElementById("message-box");


getLastMessages();

async function getLastMessages() {
    let messages;
    let promise = await fetch("/api/v1/messages");
    if (promise.ok) {
        messages = await promise.json();
        for (let i = 0; i < messages.length; i++) {
            printMessage(box, messages[i]);
        }
    } else window.alert("GG");

}

function printNewAuthor(pContent, message, lastMessage, messageBox) {
    let pAuthor = document.createElement("p");
    let image = document.createElement("img");
    let aRef = document.createElement("a");
    let authorContainer = document.createElement("div");
    aRef.href = '/profile/' + message.login;
    aRef.appendChild(image);
    image.src = message.imagePath;
    pAuthor.textContent = message.nickname;
    pAuthor.className = 'message-author';
    image.className = "author-pic";

    let userLogin = sessionStorage.getItem("userLogin")
    if (message.login === userLogin) {
        authorContainer.className = "author-container self"
        pAuthor.className = "message-author self"
        authorContainer.append(pAuthor);
        authorContainer.append(aRef);
    } else {
        pAuthor.className = "message-author"
        authorContainer.className = "author-container"
        authorContainer.append(aRef);
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
   let result = prepareMessage(box,message);
   if (result) {
       box.append(result);
       // box.scrollTop = box.scrollHeight;
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
    let object = {};
    data.forEach((value, key) => object[key] = value);
    let json = JSON.stringify(object);
    return await fetch('/api/v1/messages', {
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-Token": data.get("_csrf")
        },
        method: 'POST',
        body: json
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
            window.alert("Войди пожалуйста! ;)");
        }

        let box = document.getElementById('messageInput');
        box.value = '';
    }
}

//SCROLL HANDLER

document.addEventListener('DOMContentLoaded', function () {
    const chatContainer = document.getElementById('message-box'); // Замените на ваш ID контейнера
    let isLoading = false;

    chatContainer.addEventListener('scroll', function () {
        if (chatContainer.scrollTop === 0 && !isLoading) {
            isLoading = true;
            loadOlderMessages().then(() => {
                isLoading = false;
            }).catch((error) => {
                console.error('Error loading older messages:', error);
                isLoading = false;
            });
        }
    });

    function loadOlderMessages() {
        return new Promise((resolve, reject) => {
            let firstMessageId = chatContainer.firstElementChild.messageId;
            fetch('/api/v1/messages/' + firstMessageId, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    // Обработайте полученные данные и добавьте старые сообщения в контейнер
                    // Например, data.messages содержит массив старых сообщений
                    const currentScrollHeight = chatContainer.scrollHeight;
                    data.forEach(message => {
                        printOldMessage(box, message);
                    });

                    // Обновите позицию прокрутки
                    chatContainer.scrollTop = chatContainer.scrollHeight - currentScrollHeight;

                    resolve();
                })
                .catch(error => {
                    reject(error);
                });
        });
    }
});

function printOldMessage(box, message) {

    let result = prepareMessage(box, message);
    if (result) {
        box.prepend(result);
        box.scrollTop = box.scrollHeight;
    }
}

function prepareMessage(box, message) {
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
    } else {
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

        printNewAuthor(pContent, message, lastMessage, messageContainer);
        messageContainer.append(divMessage);
        divMessage.append(pContent, pDate)
        return messageContainer;
    }
    return null;
}


