totalCustomerCount(0);
totalItemCount(0);
totalOrderCount(0);

export function totalCustomerCount(count){
    $('#total-customers').text(count);
}
export function totalItemCount(count){
    $('#total-Items').text(count);
}
export function totalOrderCount(count){
    $('#total-Orders').text(count);
}
function updateDigitalTime() {
    const digitalTimeLabel = document.getElementById("digitalTimeLabel");
    const now = new Date();
    const hours = now.getHours().toString().padStart(2, "0");
    const minutes = now.getMinutes().toString().padStart(2, "0");
    const seconds = now.getSeconds().toString().padStart(2, "0");
    const digitalTime = `${hours}:${minutes}:${seconds}`; 
    digitalTimeLabel.textContent = digitalTime;
  }
  updateDigitalTime();
  setInterval(updateDigitalTime, 1000);
  