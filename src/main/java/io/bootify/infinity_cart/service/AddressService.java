package io.bootify.infinity_cart.service;

import io.bootify.infinity_cart.domain.Address;
import io.bootify.infinity_cart.domain.User;
import io.bootify.infinity_cart.domain.UserAddress;
import io.bootify.infinity_cart.model.AddressDTO;
import io.bootify.infinity_cart.repos.AddressRepository;
import io.bootify.infinity_cart.repos.UserAddressRepository;
import io.bootify.infinity_cart.repos.UserRepository;
import io.bootify.infinity_cart.util.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    private UserAddressRepository userAddressRepository;

    @Autowired
    private UserRepository userRepository;

    public AddressService(final AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<AddressDTO> findAll() {
        final List<Address> addresses = addressRepository.findAll(Sort.by("id"));
        return addresses.stream()
                .map(address -> mapToDTO(address, new AddressDTO()))
                .toList();
    }

    public AddressDTO get(final Long id) {
        return addressRepository.findById(id)
                .map(address -> mapToDTO(address, new AddressDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AddressDTO addressDTO) {
        final Address address = new Address();
        User user = userRepository.findByUsername(addressDTO.getUsername());
        UserAddress userAddress=new UserAddress();
        userAddress.setUser(user);
        userAddress.setAddress(address);
        mapToEntity(addressDTO, address);
        addressRepository.save(address);
        return userAddressRepository.save(userAddress).getId();
    }

    public void update(final Long id, final AddressDTO addressDTO) {
        final Address address = addressRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(addressDTO, address);
        addressRepository.save(address);
    }

    @Transactional
    public boolean delete(final String username,final Long id) {
        if (addressRepository.existsById(id) && userAddressRepository.existsByUserAndAddress(userRepository.findByUsername(username),addressRepository.findById(id).get())) {
            userAddressRepository.deleteByAddress(addressRepository.getReferenceById(id));
            addressRepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }

    private AddressDTO mapToDTO(final Address address, final AddressDTO addressDTO) {
        addressDTO.setName(address.getName());
        addressDTO.setPhone(address.getPhone());
        addressDTO.setId(address.getId());
        addressDTO.setLine1(address.getLine1());
        addressDTO.setLine2(address.getLine2());
        addressDTO.setCity(address.getCity());
        addressDTO.setState(address.getState());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setPincode(address.getPincode());
        addressDTO.setType(address.getType());
        return addressDTO;
    }

    private Address mapToEntity(final AddressDTO addressDTO, final Address address) {
        address.setName(addressDTO.getName());
        address.setPhone(addressDTO.getPhone());
        address.setLine1(addressDTO.getLine1());
        address.setLine2(addressDTO.getLine2());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());
        address.setPincode(addressDTO.getPincode());
        address.setType(addressDTO.getType());
        return address;
    }

    public List<AddressDTO> getAllAddresses(String username) {
        User user = userRepository.findByUsername(username);
        List<Address> addresses = new ArrayList<>();
        List<UserAddress> userAddresses = userAddressRepository.getAllAddressByUser(user);
        for(UserAddress userAddress:userAddresses){
            addresses.add(userAddress.getAddress());
        }
        return addresses.stream()
                .map(address -> mapToDTO(address, new AddressDTO()))
                .toList();
    }
}
