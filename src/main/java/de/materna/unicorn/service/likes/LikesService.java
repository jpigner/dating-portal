package de.materna.unicorn.service.likes;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.materna.unicorn.model.Likes;
import de.materna.unicorn.repository.likes.LikesRepository;

/**
 * Represents a service to read messages from the repository.
 */
public class LikesService implements LikesRepository {
	@Inject
	private EntityManager em;

	@Override
	public Likes findById(long id) {
		return em.find(Likes.class, id);
	}

	@Override
	public List<Likes> findAll() {
		String jpql = "SELECT likes FROM Likes likes";
		TypedQuery<Likes> query = em.createQuery(jpql, Likes.class);
		return query.getResultList();
	}
}