// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package cs.ut.domain;

import cs.ut.domain.ApplicationConversionServiceFactoryBean;
import cs.ut.domain.Invoice;
import cs.ut.domain.PlantHireRequest;
import cs.ut.domain.Site;
import cs.ut.domain.SiteEngineer;
import cs.ut.domain.Supplier;
import cs.ut.domain.WorksEngineer;
import cs.ut.repository.PlantHireRequestRepository;
import cs.ut.repository.SiteEngineerRepository;
import cs.ut.security.Assignments;
import cs.ut.security.Authorities;
import cs.ut.security.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    declare @type: ApplicationConversionServiceFactoryBean: @Configurable;
    
    @Autowired
    PlantHireRequestRepository ApplicationConversionServiceFactoryBean.plantHireRequestRepository;
    
    @Autowired
    SiteEngineerRepository ApplicationConversionServiceFactoryBean.siteEngineerRepository;
    
    public Converter<Invoice, String> ApplicationConversionServiceFactoryBean.getInvoiceToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<cs.ut.domain.Invoice, java.lang.String>() {
            public String convert(Invoice invoice) {
                return new StringBuilder().append(invoice.getPurchaseOrderHRef()).append(' ').append(invoice.getPurchaseOrderId()).toString();
            }
        };
    }
    
    public Converter<Long, Invoice> ApplicationConversionServiceFactoryBean.getIdToInvoiceConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, cs.ut.domain.Invoice>() {
            public cs.ut.domain.Invoice convert(java.lang.Long id) {
                return Invoice.findInvoice(id);
            }
        };
    }
    
    public Converter<String, Invoice> ApplicationConversionServiceFactoryBean.getStringToInvoiceConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, cs.ut.domain.Invoice>() {
            public cs.ut.domain.Invoice convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Invoice.class);
            }
        };
    }
    
    public Converter<PlantHireRequest, String> ApplicationConversionServiceFactoryBean.getPlantHireRequestToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<cs.ut.domain.PlantHireRequest, java.lang.String>() {
            public String convert(PlantHireRequest plantHireRequest) {
                return new StringBuilder().append(plantHireRequest.getStartDate()).append(' ').append(plantHireRequest.getEndDate()).append(' ').append(plantHireRequest.getTotalCost()).append(' ').append(plantHireRequest.getComment()).toString();
            }
        };
    }
    
    public Converter<Long, PlantHireRequest> ApplicationConversionServiceFactoryBean.getIdToPlantHireRequestConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, cs.ut.domain.PlantHireRequest>() {
            public cs.ut.domain.PlantHireRequest convert(java.lang.Long id) {
                return plantHireRequestRepository.findOne(id);
            }
        };
    }
    
    public Converter<String, PlantHireRequest> ApplicationConversionServiceFactoryBean.getStringToPlantHireRequestConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, cs.ut.domain.PlantHireRequest>() {
            public cs.ut.domain.PlantHireRequest convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), PlantHireRequest.class);
            }
        };
    }
    
    public Converter<Site, String> ApplicationConversionServiceFactoryBean.getSiteToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<cs.ut.domain.Site, java.lang.String>() {
            public String convert(Site site) {
                return new StringBuilder().append(site.getName()).toString();
            }
        };
    }
    
    public Converter<Long, Site> ApplicationConversionServiceFactoryBean.getIdToSiteConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, cs.ut.domain.Site>() {
            public cs.ut.domain.Site convert(java.lang.Long id) {
                return Site.findSite(id);
            }
        };
    }
    
    public Converter<String, Site> ApplicationConversionServiceFactoryBean.getStringToSiteConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, cs.ut.domain.Site>() {
            public cs.ut.domain.Site convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Site.class);
            }
        };
    }
    
    public Converter<SiteEngineer, String> ApplicationConversionServiceFactoryBean.getSiteEngineerToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<cs.ut.domain.SiteEngineer, java.lang.String>() {
            public String convert(SiteEngineer siteEngineer) {
                return new StringBuilder().append(siteEngineer.getFirstName()).append(' ').append(siteEngineer.getLastName()).append(' ').append(siteEngineer.getEmail()).toString();
            }
        };
    }
    
    public Converter<Long, SiteEngineer> ApplicationConversionServiceFactoryBean.getIdToSiteEngineerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, cs.ut.domain.SiteEngineer>() {
            public cs.ut.domain.SiteEngineer convert(java.lang.Long id) {
                return siteEngineerRepository.findOne(id);
            }
        };
    }
    
    public Converter<String, SiteEngineer> ApplicationConversionServiceFactoryBean.getStringToSiteEngineerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, cs.ut.domain.SiteEngineer>() {
            public cs.ut.domain.SiteEngineer convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), SiteEngineer.class);
            }
        };
    }
    
    public Converter<Supplier, String> ApplicationConversionServiceFactoryBean.getSupplierToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<cs.ut.domain.Supplier, java.lang.String>() {
            public String convert(Supplier supplier) {
                return new StringBuilder().append(supplier.getName()).toString();
            }
        };
    }
    
    public Converter<Long, Supplier> ApplicationConversionServiceFactoryBean.getIdToSupplierConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, cs.ut.domain.Supplier>() {
            public cs.ut.domain.Supplier convert(java.lang.Long id) {
                return Supplier.findSupplier(id);
            }
        };
    }
    
    public Converter<String, Supplier> ApplicationConversionServiceFactoryBean.getStringToSupplierConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, cs.ut.domain.Supplier>() {
            public cs.ut.domain.Supplier convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Supplier.class);
            }
        };
    }
    
    public Converter<WorksEngineer, String> ApplicationConversionServiceFactoryBean.getWorksEngineerToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<cs.ut.domain.WorksEngineer, java.lang.String>() {
            public String convert(WorksEngineer worksEngineer) {
                return new StringBuilder().append(worksEngineer.getFirstName()).append(' ').append(worksEngineer.getLastName()).append(' ').append(worksEngineer.getEmail()).toString();
            }
        };
    }
    
    public Converter<Long, WorksEngineer> ApplicationConversionServiceFactoryBean.getIdToWorksEngineerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, cs.ut.domain.WorksEngineer>() {
            public cs.ut.domain.WorksEngineer convert(java.lang.Long id) {
                return WorksEngineer.findWorksEngineer(id);
            }
        };
    }
    
    public Converter<String, WorksEngineer> ApplicationConversionServiceFactoryBean.getStringToWorksEngineerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, cs.ut.domain.WorksEngineer>() {
            public cs.ut.domain.WorksEngineer convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WorksEngineer.class);
            }
        };
    }
    
    public Converter<Assignments, String> ApplicationConversionServiceFactoryBean.getAssignmentsToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<cs.ut.security.Assignments, java.lang.String>() {
            public String convert(Assignments assignments) {
                return "(no displayable fields)";
            }
        };
    }
    
    public Converter<Long, Assignments> ApplicationConversionServiceFactoryBean.getIdToAssignmentsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, cs.ut.security.Assignments>() {
            public cs.ut.security.Assignments convert(java.lang.Long id) {
                return Assignments.findAssignments(id);
            }
        };
    }
    
    public Converter<String, Assignments> ApplicationConversionServiceFactoryBean.getStringToAssignmentsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, cs.ut.security.Assignments>() {
            public cs.ut.security.Assignments convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Assignments.class);
            }
        };
    }
    
    public Converter<Authorities, String> ApplicationConversionServiceFactoryBean.getAuthoritiesToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<cs.ut.security.Authorities, java.lang.String>() {
            public String convert(Authorities authorities) {
                return new StringBuilder().append(authorities.getAuthority()).toString();
            }
        };
    }
    
    public Converter<Long, Authorities> ApplicationConversionServiceFactoryBean.getIdToAuthoritiesConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, cs.ut.security.Authorities>() {
            public cs.ut.security.Authorities convert(java.lang.Long id) {
                return Authorities.findAuthorities(id);
            }
        };
    }
    
    public Converter<String, Authorities> ApplicationConversionServiceFactoryBean.getStringToAuthoritiesConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, cs.ut.security.Authorities>() {
            public cs.ut.security.Authorities convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Authorities.class);
            }
        };
    }
    
    public Converter<Users, String> ApplicationConversionServiceFactoryBean.getUsersToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<cs.ut.security.Users, java.lang.String>() {
            public String convert(Users users) {
                return new StringBuilder().append(users.getUsername()).append(' ').append(users.getPassword()).toString();
            }
        };
    }
    
    public Converter<Long, Users> ApplicationConversionServiceFactoryBean.getIdToUsersConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, cs.ut.security.Users>() {
            public cs.ut.security.Users convert(java.lang.Long id) {
                return Users.findUsers(id);
            }
        };
    }
    
    public Converter<String, Users> ApplicationConversionServiceFactoryBean.getStringToUsersConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, cs.ut.security.Users>() {
            public cs.ut.security.Users convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Users.class);
            }
        };
    }
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getInvoiceToStringConverter());
        registry.addConverter(getIdToInvoiceConverter());
        registry.addConverter(getStringToInvoiceConverter());
        registry.addConverter(getPlantHireRequestToStringConverter());
        registry.addConverter(getIdToPlantHireRequestConverter());
        registry.addConverter(getStringToPlantHireRequestConverter());
        registry.addConverter(getSiteToStringConverter());
        registry.addConverter(getIdToSiteConverter());
        registry.addConverter(getStringToSiteConverter());
        registry.addConverter(getSiteEngineerToStringConverter());
        registry.addConverter(getIdToSiteEngineerConverter());
        registry.addConverter(getStringToSiteEngineerConverter());
        registry.addConverter(getSupplierToStringConverter());
        registry.addConverter(getIdToSupplierConverter());
        registry.addConverter(getStringToSupplierConverter());
        registry.addConverter(getWorksEngineerToStringConverter());
        registry.addConverter(getIdToWorksEngineerConverter());
        registry.addConverter(getStringToWorksEngineerConverter());
        registry.addConverter(getAssignmentsToStringConverter());
        registry.addConverter(getIdToAssignmentsConverter());
        registry.addConverter(getStringToAssignmentsConverter());
        registry.addConverter(getAuthoritiesToStringConverter());
        registry.addConverter(getIdToAuthoritiesConverter());
        registry.addConverter(getStringToAuthoritiesConverter());
        registry.addConverter(getUsersToStringConverter());
        registry.addConverter(getIdToUsersConverter());
        registry.addConverter(getStringToUsersConverter());
    }
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
}
