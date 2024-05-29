loadProfileData();

async function loadProfileData(){
    let response = await fetch("api/v1/profile/", {
        method: 'GET'
    });
    if (response.ok) {
        let profile = await response.json();
        printProfileData(profile)

    }
    else {

    }
}

function printProfileData(profile){
    printUsername(profile);
    printImage(profile);
    printRole(profile);
}

function printImage(profile){
    const imgContainer = document.querySelector(".img-container");
    let image = document.querySelector(".profile-picture");
    image.src = profile.userPic;
    image.style.opacity = "1";
}

function printUsername(profile){
    const usernameContainer = document.querySelector(".username-container");

    let username = document.createElement("h2");
    username.className = "username";
    username.textContent = profile.login;

    usernameContainer.append(username);
}

function printRole(profile){
    const role = document.querySelector("#role");

    role.textContent = profile.role;

}
