package co.uniquindio.edu.sendifly.models;

public class Pack {
    private String id;
    private float weight;
    private float volume;

    public Pack(PackageBuilder build){
        this.id = build.id;
        this.weight = build.weight;
        this.volume = build.volume;
    }

    public static class PackageBuilder{
        private String id;
        private float weight;
        private float volume;

        public PackageBuilder id(String id){
            this.id = id;
            return this;}

        public PackageBuilder weight(float weight){
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

    public void setVolume(float Volume) {
        this.volume = volume;}

    public float getVolume() {
        return volume;
    }

    public float getWeight() {
        return weight;}

    public void setWeight(float weight) {
        this.weight = weight;}

    public double getVolumetricWeight() {
        return volume * 200;
    }
}
