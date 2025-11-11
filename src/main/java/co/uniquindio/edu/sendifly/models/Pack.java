package co.uniquindio.edu.sendifly.models;

public class Pack {
    private String id;
    private String product;
    private float price;
    private float weight;
    private float volume;

    public Pack(PackageBuilder build){
        this.id = build.id;
        this.product = build.product;
        this.price = build.price;
        this.weight = build.weight;
        this.volume = build.volume;
    }

    public static class PackageBuilder{
        private String id;
        private String product;
        private float price;
        private float weight;
        private float volume;

        public PackageBuilder id(String id){
            this.id = id;
            return this;}

        public PackageBuilder product(String product){
            this.product = product;
            return this;}

        public PackageBuilder price(float price){
            this.price = price;
            return this;}

        public PackageBuilder height(float weight){
            this.weight = weight;
            return this;}

        public PackageBuilder volume(float volume){
            this.volume = volume;
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

    public void setVolume(float Volume) {
        this.volume = volume;}

    public float getVolume() {
        return volume;
    }

    public float getWeight() {
        return weight;}

    public void setWeight(float weight) {
        this.weight = weight;}



}
