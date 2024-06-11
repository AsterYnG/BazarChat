loadProfileData();

async function loadProfileData() {
    let currentLocation = window.location.pathname;
    const resources = currentLocation.split("/");
    if (resources.length === 4) {
        let response = await fetch("/api/v1/profile/", {
            method: 'GET'
        });
        if (response.ok) {
            let profile = await response.json();
            printProfileData(profile)

        } else {

        }
    } else {
        let response = await fetch('/api/v1'+currentLocation, {
            method: 'GET'
        });
        if (response.ok) {
            let profile = await response.json();
            printProfileData(profile)

        } else {

        }
    }
}

function printProfileData(profile) {
    printNickname(profile);
    printImage(profile);
    printRole(profile);
    printEmail(profile);
    printFullName(profile);
    printLogin(profile);
}

function printImage(profile) {
    let image = document.querySelector(".profile-picture");
    image.src = profile.userPic;
    image.style.opacity = "1";
}

function printNickname(profile) {
    let username = document.querySelector(".nickname");
    username.textContent = profile.nickname;
    username.style.display = "block";
}

function printEmail(profile) {
    let email = document.querySelector("#email");
    email.textContent = profile.email;
}

function printLogin(profile) {
    let login = document.querySelector("#login");
    login.textContent = profile.login;
}


function printFullName(profile) {
    let fullName = document.querySelector("#fullName");

    fullName.textContent = profile.name + " " + profile.surname;
}

function printRole(profile) {
    const role = document.querySelector(".diagonal-text");

    role.textContent = profile.role;

}
