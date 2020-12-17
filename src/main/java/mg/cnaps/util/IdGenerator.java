package mg.cnaps.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.springframework.stereotype.Service;

@Service
public class IdGenerator implements IdentifierGenerator, Configurable {
	
    private String sequenceCallSyntax;
	
	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException { 
        final String sequencePerEntitySuffix = ConfigurationHelper.getString(
                SequenceStyleGenerator.CONFIG_SEQUENCE_PER_ENTITY_SUFFIX,
                params,
                SequenceStyleGenerator.DEF_SEQUENCE_SUFFIX);
 
        final String defaultSequenceName = ConfigurationHelper.getBoolean(
                SequenceStyleGenerator.CONFIG_PREFER_SEQUENCE_PER_ENTITY,
                params,
                false)
                ? params.getProperty(JPA_ENTITY_NAME) + sequencePerEntitySuffix
                : SequenceStyleGenerator.DEF_SEQUENCE_NAME;
 
        sequenceCallSyntax = "select nextval('\"RFM\"."+
                ConfigurationHelper.getString(
                        SequenceStyleGenerator.SEQUENCE_PARAM,
                        params,
                        defaultSequenceName)+"')";
		
	}

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		Session sess=session.getFactory().openSession();
        long seqValue = ((Number)sess.createNativeQuery(sequenceCallSyntax).uniqueResult()).intValue();
        sess.close();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        int id=Integer.parseInt(sdf.format(new java.util.Date())+String.format("%03d",seqValue));
        return (Serializable) id;
    }
}
