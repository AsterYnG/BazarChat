const registrationForm  = document.getElementById('registration-form');
if (registrationForm){
    registrationForm.addEventListener('submit',sendMessage);
}
else window.alert("pizdec");
async function sendData(data) {

    return fetch('api/v1/users', {
        method: 'POST',
        body: data
    })
}

async function sendMessage(event){


    event.preventDefault();
    let data = new FormData(event.target);
    let response = await sendData(data);
    if (!response.ok){
        window.alert("Form pushing error");
    }else{
        window.location.href = '/login'
    }


}

