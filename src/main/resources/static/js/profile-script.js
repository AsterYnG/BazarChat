loadProfileData();

async function loadProfileData(){
    let response = await fetch("/api/v1/profile/", {
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
    let image = document.querySelector(".profile-picture");
    image.src = profile.userPic;
    image.style.opacity = "1";
}

function printUsername(profile){
    let username = document.querySelector(".username");
    username.textContent = profile.login;
    username.style.display = "block";
}

function printRole(profile){
    const role = document.querySelector(".diagonal-text");

    role.textContent = profile.role;

}
