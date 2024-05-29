getCurrentUser();

function loadUser(userId,userLogin) {
    sessionStorage.setItem("userId",userId);
    sessionStorage.setItem("userLogin",userLogin);
}

function loadUserImage(currentUser) {
    let userPicContainer = document.getElementById("user-pic-container");
    let userPic = document.createElement("img");
    userPic.className = "user-pic"


    if (currentUser.userPic === null) {
        userPic.src = 'images/default.png'
    } else {
        userPic.src =currentUser.userPic;
    }
    userPicContainer.append(userPic);
}

async function getCurrentUser(){
    let response = await fetch("api/v1/users/current", {
        method: 'GET'
    });
    if (response.ok) {
        let currentUser = await response.json();

        loadUser(currentUser.id,currentUser.login);
        loadUserImage(currentUser);
    }
    else {
        loadUser(null,null);
        loadUserImage(null);
    }
}

