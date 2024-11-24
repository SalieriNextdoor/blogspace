import {createContext} from 'react';
import {ProjectContextType} from '../types'

const projectContext = createContext<ProjectContextType | null>(null);

export default projectContext;
