package co.uniquindio.edu.sendifly.models;

public class Pack {
    private String id;
    private String product;
    private float price;
    private float width;
    private float length;
    private float height;
    private float weight;

    public Pack(PackageBuilder build){
        this.id = build.id;
        this.product = build.product;
        this.price = build.price;
        this.width = build.width;
        this.length = build.length;
        this.height = build.height;
        this.weight = build.weight;
    }

    public static class PackageBuilder{
        private String id;
        private String product;
        private float price;
        private float width;
        private float length;
        private float height;
        private float weight;

        public PackageBuilder id(String id){
            this.id = id;
            return this;}

        public PackageBuilder product(String product){
            this.product = product;
            return this;}

        public PackageBuilder price(float price){
            this.price = price;
            return this;}

        public PackageBuilder width(float width){
            this.width = width;
            return this;}

        public PackageBuilder length(float length){
            this.length = length;
            return this;}

        public PackageBuilder height(float height){
            this.height = height;
            return this;}

        public PackageBuilder weight(float weight){
            this.weight = weight;
            return this;}

        public Pack build(){
            return new Pack(this);
        }
    }

    public String getId() {
        return id;}

    public void setId(String id) {
        this.id = id;}

    public String getProduct() {
        return product;}

    public void setProduct(String product) {
        this.product = product;}

    public float getPrice() {
        return price;}

    public void setPrice(float price) {
        this.price = price;}

    public float getWidth() {
        return width;}

    public void setWidth(float width) {
        this.width = width;}

    public float getLength() {
        return length;}

    public void setLength(float length) {
        this.length = length;}

    public float getHeight() {
        return height;}

    public void setHeight(float height) {
        this.height = height;}

    public float getWeight() {
        return weight;}

    public void setWeight(float weight) {
        this.weight = weight;}



}
