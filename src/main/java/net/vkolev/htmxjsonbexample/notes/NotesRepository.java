package net.vkolev.htmxjsonbexample.notes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends JpaRepository<Note, Long> {
    @Query(value = """
            SELECT * FROM note WHERE jsonb_path_exists(translations, ('$.'|| :translation ||'')::jsonpath) IS true""",
            nativeQuery = true)
    List<Note> findAllWithTranslation(@Param("translation") String translation);

    @Query(value = """
SELECT * FROM note 
         WHERE jsonb_path_exists(translations, ('$.'|| :translation ||'')::jsonpath) IS true 
           AND translations::text ILIKE '%'||:q||'%'
""",
    nativeQuery = true)
    List<Note> findAllWithTranslationAndQuery(@Param("translation") String translation, @Param("q") String q);
}
