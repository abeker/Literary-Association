package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.dto.response.MembershipDTO;
import com.lu.literaryassociation.dto.response.UserMembershipDTO;
import com.lu.literaryassociation.dto.response.UserMembershipsDTO;
import com.lu.literaryassociation.entity.Membership;
import com.lu.literaryassociation.entity.User;
import com.lu.literaryassociation.entity.UserMembership;
import com.lu.literaryassociation.repository.IMembershipRepository;
import com.lu.literaryassociation.repository.IUserMembershipRepository;
import com.lu.literaryassociation.repository.IUserRepository;
import com.lu.literaryassociation.security.TokenUtils;
import com.lu.literaryassociation.services.definition.IUserMembershipService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserMembershipService implements IUserMembershipService {

    private final IUserMembershipRepository _userMembershipRepository;
    private final IMembershipRepository _membershipRepository;
    private final TokenUtils _tokenUtils;
    private final IUserRepository _userRepository;

    public UserMembershipService(IUserMembershipRepository userMembershipRepository, IMembershipRepository membershipRepository, TokenUtils tokenUtils, IUserRepository userRepository) {
        _userMembershipRepository = userMembershipRepository;
        _membershipRepository = membershipRepository;
        _tokenUtils = tokenUtils;
        _userRepository = userRepository;
    }

    @Override
    public UserMembershipsDTO getMembershipsForPeriod(String token, int forPeriodInDays) {
        List<UserMembership> userMembershipsForPeriod = getUserMembershipsForPeriod(getUserFromToken(token), forPeriodInDays);
        List<UserMembershipDTO> retUserMemberships = new ArrayList<>();
        for (UserMembership userMembership : userMembershipsForPeriod) {
            retUserMemberships.add(mapUserMembershipToDTO(userMembership));
        }
        return mepUserMembershipListToUserMembershipsDTO(retUserMemberships, checkWhetherNeededToPay(token, userMembershipsForPeriod));
    }

    private UserMembershipsDTO mepUserMembershipListToUserMembershipsDTO(List<UserMembershipDTO> userMemberships, boolean isNeedToPay) {
        return UserMembershipsDTO.builder()
                .needToPay(isNeedToPay)
                .userMemberships(userMemberships)
                .build();
    }

    private boolean checkWhetherNeededToPay(String token, List<UserMembership> userMemberships) {
        if(userMemberships.isEmpty()) {
            return true;
        }
        UserMembership currentlyUserMembership = userMemberships.get(0);
        for (UserMembership userMembership : userMemberships) {
            if(userMembership.getPaymentDate().isAfter(currentlyUserMembership.getPaymentDate())) {
                currentlyUserMembership = userMembership;
            }
        }
        return currentlyUserMembership.getPaymentDate().plusDays(30).isBefore(LocalDateTime.now());
    }

    @Override
    public UserMembershipsDTO payUserMembership(String token, String membershipIdString) {
        UUID membershipId = UUID.fromString(membershipIdString);
        if(membershipIdString.equals("empty")) {
            membershipId = _membershipRepository.findAll().get(0).getId();
        }
        UserMembership userMembership = new UserMembership();
        userMembership.setMembership(getMembershipFromId(membershipId));
        userMembership.setPaymentDate(LocalDateTime.now());
        userMembership.setUser(getUserFromToken(token));
        _userMembershipRepository.save(userMembership);
        return getMembershipsForPeriod(token, Integer.MAX_VALUE);
    }

    @Override
    public boolean isUserPaidMembership(UUID userId, int membershipDuration) {
        List<UserMembership> allUserMemberships = _userMembershipRepository.findAll()
                .stream()
                .filter(userMembership -> userMembership.getUser().getId().equals(userId) &&
                        userMembership.getPaymentDate().plusDays(membershipDuration).isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());

        return !allUserMemberships.isEmpty();
    }

    private User getUserFromToken(String token) {
        String username = _tokenUtils.getUsernameFromToken(token);
        return _userRepository.findOneByUsername(username);
    }

    private Membership getMembershipFromId(UUID membershipId) {
        Optional<Membership> membershipOptional = _membershipRepository.findById(membershipId);
        if(membershipOptional.isPresent()) {
            Membership membership = membershipOptional.get();
            if(membership.getDateClosed() == null) {
                return membership;
            }
        }
        return null;
    }

    private UserMembershipDTO mapUserMembershipToDTO(UserMembership userMembership) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return UserMembershipDTO.builder()
                .id(userMembership.getId().toString())
                .paymentDate(userMembership.getPaymentDate().format(formatter))
                .membership(mapMembershipToDTO(userMembership.getMembership()))
                .build();
    }

    private MembershipDTO mapMembershipToDTO(Membership membership) {
        return MembershipDTO.builder()
                .id(membership.getId().toString())
                .amount(membership.getAmount())
                .dateClosed(membership.getDateClosed() != null ? membership.getDateClosed().toString() : null)
                .dateOpened(membership.getDateOpened().toString())
                .build();
    }

    private List<UserMembership> getUserMembershipsForPeriod(User user, int forPeriodInDays) {
        return _userMembershipRepository.findAll()
                    .stream()
                    .filter(userMembership -> userMembership.getUser().getId().equals(user.getId()) &&
                                userMembership.getPaymentDate().plusDays(forPeriodInDays).isAfter(LocalDateTime.now()))
                    .collect(Collectors.toList());
    }

}
