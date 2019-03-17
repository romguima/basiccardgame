package org.romguima.cardgame.model.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.romguima.cardgame.model.Player;
import org.romguima.cardgame.model.repository.PlayerRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @Test
    public void shouldSavePlayer() {
        Player player = new Player();
        player.setName("Test");

        playerService.create(player);

        verify(playerRepository).save(player);
    }

    @Test
    public void shouldReturnRetrievedPlayer() {
        Player player = new Player();
        player.setName("Test");
        given(playerRepository.findById(player.getId())).willReturn(Optional.of(player));

        Player result = playerService.retrieve(player.getId());

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Test");
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenRetrievedValueIsEmpty() {
        given(playerRepository.findById("invalid")).willReturn(Optional.empty());

        playerService.retrieve("invalid");
    }
}