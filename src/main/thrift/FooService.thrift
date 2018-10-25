#@namespace scala org.minyo.services

struct DeliveryInformation {
    1: binary data
    2: string filePath
}

service FooService {
    bool deliver(1: DeliveryInformation deliveryInformation);
}