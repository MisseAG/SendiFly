package co.uniquindio.edu.sendifly.models;

import co.uniquindio.edu.sendifly.models.PaymentMethods.PaymentMethod;

import java.time.LocalDate;

public class Payment {
    private String id;
    private LocalDate date;
    private boolean result;
    private User user;
    private PaymentMethod paymentMethod;
    private Fee fee;

    public Payment(PaymentBuilder build){
        this.id = build.id;
        this.date = build.date;
        this.result = build.result;
        this.user = build.user;
        this.paymentMethod = build.paymentMethod;
        this.fee = build.fee;
    }

    public static class PaymentBuilder{
        private String id;
        private LocalDate date;
        private boolean result;
        private User user;
        private PaymentMethod paymentMethod;
        private Fee fee;

        public PaymentBuilder id(String id){
            this.id=id;
            return this;}

        public PaymentBuilder date(LocalDate date){
            this.date=date;
            return this;}

        public PaymentBuilder result(boolean result){
            this.result=result;
            return this;}

        public PaymentBuilder user(User user){
            this.user=user;
            return this;}

        public PaymentBuilder paymentMethod(PaymentMethod paymentMethod){
            this.paymentMethod=paymentMethod;
            return this;}

        public PaymentBuilder fee(Fee fee){
            this.fee=fee;
            return this;}

        public Payment build(){
            return new Payment(this);
        }
    }

    public String getId() {
        return id;}

    public void setId(String id) {
        this.id = id;}

    public LocalDate getDate() {
        return date;}

    public void setDate(LocalDate date) {
        this.date = date;}

    public boolean isResult() {
        return result;}

    public void setResult(boolean result) {
        this.result = result;}

    public User getUser() {
        return user;}

    public void setUser(User user) {
        this.user = user;}

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;}

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;}

    public Fee getFee() {
        return fee;}

    public void setFee(Fee fee) {
        this.fee = fee;}


}
