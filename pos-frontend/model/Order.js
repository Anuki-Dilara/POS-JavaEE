export class Order{
    constructor(OrderId, OrderDate, CustomerId, Items, Discount, Total){
        this.OrderId = OrderId;
        this.OrderDate = OrderDate;
        this.CustomerId = CustomerId;
        this.Items = Items;
        this.Discount = Discount;
        this.Total = Total;
    }
}