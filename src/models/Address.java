package models;

public class Address {
        private String district;
        private String city;
        private String state;
        private String address;

        public Address(String address, String district, String city, String state) {
            this.address = address;
            this.district = district;
            this.city = city;
            this.state = state;
        }

        // Getters and Setters
        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

}
