//Инициализация елементов на странице
const onlineUsersContainer = document.getElementById("user-list");



async function getOnlineUsers() {
    let response = await fetch("api/v1/users/online");
    return await response.json();
}

function deleteOffline(userListFromServer) {
    const currentUsers = document.querySelectorAll("#user-list .user");
    currentUsers.forEach(userElement => {
        if (!userListFromServer.has(userElement.login)) {
            userElement.remove();
        }
    });

}

function printOnlineUsers(userList) {

    for (let i = 0; i < userList.length; i++) {
        if (!userExist(userList[i])) {
            let liElement = document.createElement("li");
            liElement.textContent = userList[i].login;
            liElement.login = userList[i].login;
            liElement.id = userList[i].id;
            liElement.className = "user";
            onlineUsersContainer.append(liElement);
        }
    }
}

async function proccessOnlineUsers() {
    let response = await getOnlineUsers();
    printOnlineUsers(response);
    let userListFromServer = new Set(response.map(user => user.login));
    deleteOffline(userListFromServer);
}

function userExist(user) {
    const currentUsers = document.querySelectorAll("#user-list .user");
    let exists = false;
    currentUsers.forEach(userOnPage => {
        if(userOnPage.login === user.login){
            exists = true;
        }
    })
    return exists;
}

setInterval(proccessOnlineUsers, 5000);
